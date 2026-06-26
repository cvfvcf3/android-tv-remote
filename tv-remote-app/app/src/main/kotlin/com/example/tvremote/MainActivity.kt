// TV App - MainActivity
package com.example.tvremote

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tvremote.service.RemoteService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Start Remote Service
        startRemoteService()
    }
    
    private fun startRemoteService() {
        val serviceIntent = Intent(this, RemoteService::class.java)
        startService(serviceIntent)
        finish()
    }
}
