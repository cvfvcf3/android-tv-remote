// Phone Remote App - Main Activity
package com.example.tvremote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.VolumeDown
import androidx.compose.material.icons.filled.Power
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tvremote.viewmodel.RemoteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemoteControlApp()
        }
    }
}

@Composable
fun RemoteControlApp() {
    val viewModel: RemoteViewModel = viewModel()
    val isConnected by viewModel.connectionState.collectAsState()
    val tvIP by viewModel.tvIP.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header
        Text(
            "📺 Android TV Remote",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Connection Status
        if (!isConnected) {
            ConnectionPanel(
                onConnect = { ip ->
                    viewModel.connectToTV(ip)
                },
                viewModel = viewModel
            )
        } else {
            RemoteControlPanel(tvIP, viewModel)
        }
    }
}

@Composable
fun ConnectionPanel(
    onConnect: (String) -> Unit,
    viewModel: RemoteViewModel
) {
    var ip by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .background(Color.DarkGray, shape = RoundedCornerShape(12.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "🔗 Connect to TV",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
        
        TextField(
            value = ip,
            onValueChange = { ip = it },
            label = { Text("Enter TV IP Address") },
            placeholder = { Text("e.g., 192.168.1.100") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Gray,
                focusedContainerColor = Color.Gray,
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White
            )
        )
        
        Button(
            onClick = { if (ip.isNotEmpty()) onConnect(ip) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2)
            )
        ) {
            Text(
                "Connect",
                fontSize = 16.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }
    }
}

@Composable
fun RemoteControlPanel(
    tvIP: String,
    viewModel: RemoteViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .background(Color.DarkGray, shape = RoundedCornerShape(12.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Connected Status
        Text(
            "✅ Connected to: $tvIP",
            color = Color.Green,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Power Button
        Button(
            onClick = { viewModel.powerToggle() },
            modifier = Modifier
                .size(80.dp),
            colors = ButtonDefaults.buttonColors(Color.Red),
            shape = CircleShape
        ) {
            Icon(
                Icons.Default.Power,
                contentDescription = "Power",
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }

        // Volume Controls
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { viewModel.volumeDown() },
                modifier = Modifier.size(60.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF424242)
                )
            ) {
                Icon(
                    Icons.Default.VolumeDown,
                    contentDescription = "Volume Down",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }

            Text(
                "Volume",
                color = Color.White,
                fontSize = 14.sp
            )

            Button(
                onClick = { viewModel.volumeUp() },
                modifier = Modifier.size(60.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF424242)
                )
            ) {
                Icon(
                    Icons.Default.VolumeUp,
                    contentDescription = "Volume Up",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        // Channel Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.channelDown() },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2)
                )
            ) {
                Text("CH −", fontSize = 16.sp)
            }
            Button(
                onClick = { viewModel.channelUp() },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2)
                )
            ) {
                Text("CH +", fontSize = 16.sp)
            }
        }

        // Media Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.mediaPrevious() },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(horizontal = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF388E3C)
                )
            ) {
                Text("⏮", fontSize = 16.sp)
            }
            Button(
                onClick = { viewModel.mediaPlay() },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(horizontal = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF388E3C)
                )
            ) {
                Text("⏯", fontSize = 16.sp)
            }
            Button(
                onClick = { viewModel.mediaPause() },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(horizontal = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF388E3C)
                )
            ) {
                Text("⏸", fontSize = 16.sp)
            }
            Button(
                onClick = { viewModel.mediaNext() },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(horizontal = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF388E3C)
                )
            ) {
                Text("⏭", fontSize = 16.sp)
            }
        }
    }
}
