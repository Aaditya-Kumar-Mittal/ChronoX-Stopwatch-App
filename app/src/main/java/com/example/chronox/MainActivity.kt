package com.example.chronox

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = (
                WindowManager.LayoutParams.FLAG_FULLSCREEN or
                        android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                )

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ChronoxStopWatchScreen::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}