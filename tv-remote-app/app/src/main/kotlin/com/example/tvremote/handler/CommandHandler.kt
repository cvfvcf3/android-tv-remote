// Command Handler - Android TV App
package com.example.tvremote.handler

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.util.Log
import android.view.KeyEvent
import org.json.JSONObject

class CommandHandler(private val context: Context? = null) {
    companion object {
        private const val TAG = "CommandHandler"
    }
    
    fun handleCommand(commandJson: String) {
        try {
            val json = JSONObject(commandJson)
            val command = json.getString("command")
            val action = json.getString("action")
            val value = json.optString("value", "")
            
            Log.d(TAG, "Handling command: $command, action: $action, value: $value")
            
            when (command) {
                "volume" -> handleVolumeCommand(action)
                "power" -> handlePowerCommand(action)
                "channel" -> handleChannelCommand(action, value)
                "media" -> handleMediaCommand(action)
                "input" -> handleInputCommand(action, value)
                else -> Log.w(TAG, "Unknown command: $command")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Command parsing error: ${e.message}")
        }
    }
    
    private fun handleVolumeCommand(action: String) {
        try {
            val audioManager = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
            when (action) {
                "up" -> audioManager?.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI)
                "down" -> audioManager?.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI)
                "mute" -> audioManager?.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI)
            }
            Log.d(TAG, "Volume command executed: $action")
        } catch (e: Exception) {
            Log.e(TAG, "Volume command error: ${e.message}")
        }
    }
    
    private fun handlePowerCommand(action: String) {
        try {
            when (action) {
                "on" -> Log.d(TAG, "Power on command")
                "off" -> {
                    Log.d(TAG, "Power off command")
                }
                "toggle" -> Log.d(TAG, "Power toggle command")
            }
            Log.d(TAG, "Power command executed: $action")
        } catch (e: Exception) {
            Log.e(TAG, "Power command error: ${e.message}")
        }
    }
    
    private fun handleChannelCommand(action: String, value: String) {
        try {
            when (action) {
                "up" -> sendKeyEvent(KeyEvent.KEYCODE_CHANNEL_UP)
                "down" -> sendKeyEvent(KeyEvent.KEYCODE_CHANNEL_DOWN)
                "set_number" -> {
                    value.forEach { digit ->
                        val keyCode = when (digit) {
                            '0' -> KeyEvent.KEYCODE_0
                            '1' -> KeyEvent.KEYCODE_1
                            '2' -> KeyEvent.KEYCODE_2
                            '3' -> KeyEvent.KEYCODE_3
                            '4' -> KeyEvent.KEYCODE_4
                            '5' -> KeyEvent.KEYCODE_5
                            '6' -> KeyEvent.KEYCODE_6
                            '7' -> KeyEvent.KEYCODE_7
                            '8' -> KeyEvent.KEYCODE_8
                            '9' -> KeyEvent.KEYCODE_9
                            else -> return@forEach
                        }
                        sendKeyEvent(keyCode)
                    }
                }
            }
            Log.d(TAG, "Channel command executed: $action")
        } catch (e: Exception) {
            Log.e(TAG, "Channel command error: ${e.message}")
        }
    }
    
    private fun handleMediaCommand(action: String) {
        try {
            val keyCode = when (action) {
                "play" -> KeyEvent.KEYCODE_MEDIA_PLAY
                "pause" -> KeyEvent.KEYCODE_MEDIA_PAUSE
                "stop" -> KeyEvent.KEYCODE_MEDIA_STOP
                "next" -> KeyEvent.KEYCODE_MEDIA_NEXT
                "previous" -> KeyEvent.KEYCODE_MEDIA_PREVIOUS
                else -> return
            }
            sendKeyEvent(keyCode)
            Log.d(TAG, "Media command executed: $action")
        } catch (e: Exception) {
            Log.e(TAG, "Media command error: ${e.message}")
        }
    }
    
    private fun handleInputCommand(action: String, value: String) {
        try {
            when (action) {
                "select" -> {
                    Log.d(TAG, "Selecting input: $value")
                }
                "list" -> {
                    Log.d(TAG, "Listing available inputs")
                }
            }
            Log.d(TAG, "Input command executed: $action")
        } catch (e: Exception) {
            Log.e(TAG, "Input command error: ${e.message}")
        }
    }
    
    private fun sendKeyEvent(keyCode: Int) {
        try {
            Log.d(TAG, "Sending key event: $keyCode")
        } catch (e: Exception) {
            Log.e(TAG, "Key event error: ${e.message}")
        }
    }
}
