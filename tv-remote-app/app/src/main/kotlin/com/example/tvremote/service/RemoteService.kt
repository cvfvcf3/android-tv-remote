// TV Remote Service - Android TV App
package com.example.tvremote.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.tvremote.handler.CommandHandler
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

class RemoteService : Service() {
    private var serverSocket: ServerSocket? = null
    private var serviceScope = CoroutineScope(Dispatchers.IO + Job())
    private val commandHandler = CommandHandler(this)
    
    companion object {
        private const val PORT = 9876
        private const val TAG = "RemoteService"
    }
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service created")
        startServer()
    }
    
    private fun startServer() {
        serviceScope.launch {
            try {
                serverSocket = ServerSocket(PORT)
                Log.d(TAG, "Server started on port $PORT")
                
                while (isActive) {
                    try {
                        val socket = serverSocket?.accept()
                        if (socket != null) {
                            handleClient(socket)
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Accept error: ${e.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Server error: ${e.message}")
            }
        }
    }
    
    private suspend fun handleClient(socket: Socket) {
        withContext(Dispatchers.IO) {
            try {
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                var line: String?
                
                while (reader.readLine().also { line = it } != null) {
                    line?.let {
                        Log.d(TAG, "Command received: $it")
                        commandHandler.handleCommand(it)
                    }
                }
                
                socket.close()
            } catch (e: Exception) {
                Log.e(TAG, "Client handling error: ${e.message}")
            }
        }
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service started")
        return START_STICKY
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service destroyed")
        serverSocket?.close()
        serviceScope.cancel()
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
}
