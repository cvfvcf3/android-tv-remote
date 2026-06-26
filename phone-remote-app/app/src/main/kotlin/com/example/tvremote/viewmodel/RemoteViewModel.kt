// Remote ViewModel - Phone App
package com.example.tvremote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvremote.network.WiFiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class RemoteViewModel : ViewModel() {
    private val wifiClient = WiFiClient()
    
    private val _connectionState = MutableStateFlow(false)
    val connectionState: StateFlow<Boolean> = _connectionState
    
    private val _tvIP = MutableStateFlow("")
    val tvIP: StateFlow<String> = _tvIP
    
    fun connectToTV(ipAddress: String) {
        viewModelScope.launch {
            val connected = wifiClient.connect(ipAddress)
            if (connected) {
                _connectionState.value = true
                _tvIP.value = ipAddress
            }
        }
    }
    
    fun disconnect() {
        wifiClient.disconnect()
        _connectionState.value = false
        _tvIP.value = ""
    }
    
    // Volume Commands
    fun volumeUp() {
        sendCommand("volume", "up")
    }
    
    fun volumeDown() {
        sendCommand("volume", "down")
    }
    
    fun volumeMute() {
        sendCommand("volume", "mute")
    }
    
    // Power Commands
    fun powerOn() {
        sendCommand("power", "on")
    }
    
    fun powerOff() {
        sendCommand("power", "off")
    }
    
    fun powerToggle() {
        sendCommand("power", "toggle")
    }
    
    // Channel Commands
    fun channelUp() {
        sendCommand("channel", "up")
    }
    
    fun channelDown() {
        sendCommand("channel", "down")
    }
    
    fun setChannel(channelNumber: Int) {
        sendCommand("channel", "set_number", channelNumber.toString())
    }
    
    // Media Commands
    fun mediaPlay() {
        sendCommand("media", "play")
    }
    
    fun mediaPause() {
        sendCommand("media", "pause")
    }
    
    fun mediaStop() {
        sendCommand("media", "stop")
    }
    
    fun mediaNext() {
        sendCommand("media", "next")
    }
    
    fun mediaPrevious() {
        sendCommand("media", "previous")
    }
    
    // Input Commands
    fun selectInput(inputName: String) {
        sendCommand("input", "select", inputName)
    }
    
    // Private helper
    private fun sendCommand(command: String, action: String, value: String = "") {
        viewModelScope.launch {
            try {
                val json = JSONObject().apply {
                    put("command", command)
                    put("action", action)
                    if (value.isNotEmpty()) {
                        put("value", value)
                    }
                    put("timestamp", System.currentTimeMillis())
                }
                wifiClient.sendCommand(json.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        wifiClient.disconnect()
    }
}
