// Phone Remote App - Main Activity
package com.example.tvremote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
    var isConnected by remember { mutableStateOf(false) }
    var tvIP by remember { mutableStateOf("") }
    
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
            "Android TV Remote",
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Connection Status
        if (!isConnected) {
            ConnectionPanel(
                onConnect = { ip ->
                    tvIP = ip
                    isConnected = true
                }
            )
        } else {
            RemoteControlPanel(tvIP)
        }
    }
}

@Composable
fun ConnectionPanel(onConnect: (String) -> Unit) {
    var ip by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray, shape = RoundedCornerShape(12.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Connect to TV",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        TextField(
            value = ip,
            onValueChange = { ip = it },
            label = { Text("Enter TV IP Address") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )
        
        Button(
            onClick = { if (ip.isNotEmpty()) onConnect(ip) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Connect")
        }
    }
}

@Composable
fun RemoteControlPanel(tvIP: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray, shape = RoundedCornerShape(12.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Power Button
        Button(
            onClick = { /* Send power command */ },
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 20.dp),
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
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /* Send volume down */ },
                modifier = Modifier.size(60.dp),
                shape = CircleShape
            ) {
                Icon(
                    Icons.Default.VolumeDown,
                    contentDescription = "Volume Down",
                    tint = Color.White
                )
            }

            Text(
                "Volume",
                color = Color.White,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)
            )

            Button(
                onClick = { /* Send volume up */ },
                modifier = Modifier.size(60.dp),
                shape = CircleShape
            ) {
                Icon(
                    Icons.Default.VolumeUp,
                    contentDescription = "Volume Up",
                    tint = Color.White
                )
            }
        }

        // Channel Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { /* Channel down */ }) {
                Text("CH -")
            }
            Button(onClick = { /* Channel up */ }) {
                Text("CH +")
            }
        }

        // Media Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { /* Previous */ }) {
                Text("⏮ Prev")
            }
            Button(onClick = { /* Play/Pause */ }) {
                Text("⏯ Play")
            }
            Button(onClick = { /* Next */ }) {
                Text("⏭ Next")
            }
        }

        // Connected IP
        Text(
            "Connected to: $tvIP",
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
