// WiFi Client - Phone App
package com.example.tvremote.network

import android.util.Log
import kotlinx.coroutines.*
import java.io.PrintWriter
import java.net.Socket

class WiFiClient {
    private var socket: Socket? = null
    private var writer: PrintWriter? = null
    private var isConnected = false
    
    companion object {
        private const val PORT = 9876
        private const val TAG = "WiFiClient"
    }
    
    suspend fun connect(ipAddress: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            socket = Socket(ipAddress, PORT)
            writer = PrintWriter(socket!!.getOutputStream(), true)
            isConnected = true
            Log.d(TAG, "Connected to TV at $ipAddress:$PORT")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Connection failed: ${e.message}")
            isConnected = false
            false
        }
    }
    
    suspend fun sendCommand(command: String) = withContext(Dispatchers.IO) {
        try {
            if (isConnected && writer != null) {
                writer?.println(command)
                Log.d(TAG, "Command sent: $command")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to send command: ${e.message}")
            isConnected = false
        }
    }
    
    fun disconnect() {
        try {
            writer?.close()
            socket?.close()
            isConnected = false
            Log.d(TAG, "Disconnected")
        } catch (e: Exception) {
            Log.e(TAG, "Disconnect error: ${e.message}")
        }
    }
    
    fun isConnected(): Boolean = isConnected
}
