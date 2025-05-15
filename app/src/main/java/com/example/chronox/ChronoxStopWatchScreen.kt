package com.example.chronox

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.chronox.databinding.ActivityChronoxStopWatchScreenBinding

class ChronoxStopWatchScreen : AppCompatActivity() {

    private lateinit var binding: ActivityChronoxStopWatchScreenBinding
    private var isRunning = false
    private var timerSeconds = 0
    private lateinit var handler: Handler

    private val runnable = object : Runnable {
        override fun run() {
            timerSeconds++

            // Format time
            val hours = timerSeconds / 3600
            val minutes = (timerSeconds % 3600) / 60
            val seconds = timerSeconds % 60
            val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            binding.stopwatchText.text = timeString

            // Update progress bar (0 to 100 for 1 hr)
            val progress = (timerSeconds % 3600) * 100 / 3600
            binding.stopwatchProgress.progress = progress

            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChronoxStopWatchScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(Looper.getMainLooper())

        setupListeners()
    }

    private fun setupListeners() {
        binding.stopWatchStartButton.setOnClickListener {
            if (!isRunning) {
                startTimer()
                toggleButtonStyles(start = false)
            }
        }

        binding.stopWatchStopButton.setOnClickListener {
            if (isRunning) {
                stopTimer()
                toggleButtonStyles(start = true)
            }
        }

        binding.stopWatchResetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        isRunning = true
        handler.post(runnable)
    }

    private fun stopTimer() {
        isRunning = false
        handler.removeCallbacks(runnable)
    }

    private fun resetTimer() {
        stopTimer()
        timerSeconds = 0
        binding.stopwatchText.text = "00:00:00"
        binding.stopwatchProgress.progress = 0
        toggleButtonStyles(start = true)
    }

    private fun toggleButtonStyles(start: Boolean) {
        if (start) {
            // Show start active
            setButtonStyle(
                start = true,
                startBg = R.drawable.custom_button_background1,
                startTextColor = R.color.white,
                stopBg = R.drawable.custom_button_background2,
                stopTextColor = R.color.mu_black_500_main
            )
        } else {
            // Show stop active
            setButtonStyle(
                start = false,
                startBg = R.drawable.custom_button_background2,
                startTextColor = R.color.mu_black_500_main,
                stopBg = R.drawable.custom_button_background1,
                stopTextColor = R.color.white
            )
        }
    }

    private fun setButtonStyle(
        start: Boolean,
        startBg: Int,
        startTextColor: Int,
        stopBg: Int,
        stopTextColor: Int
    ) {
        // Start Button
        val startTextView = binding.stopWatchStartButton.getChildAt(0) as? android.widget.LinearLayout
        startTextView?.setBackgroundResource(startBg)
        (startTextView?.getChildAt(0) as? android.widget.TextView)?.setTextColor(resources.getColor(startTextColor, null))

        // Stop Button
        val stopTextView = binding.stopWatchStopButton.getChildAt(0) as? android.widget.LinearLayout
        stopTextView?.setBackgroundResource(stopBg)
        (stopTextView?.getChildAt(0) as? android.widget.TextView)?.setTextColor(resources.getColor(stopTextColor, null))
    }
}
