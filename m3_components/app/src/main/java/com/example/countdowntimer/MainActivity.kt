package com.example.countdowntimer

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.countdowntimer.databinding.ActivityMainBinding
import com.google.android.material.slider.Slider
import kotlinx.coroutines.*


private const val MAX: Int = 60
private const val MIN: Int = 0

class MainActivity : AppCompatActivity() {
    private var counter: Int = 10
    private var step: Int = 0
    private lateinit var buttonStart: Button
    private lateinit var buttonStop: Button
    private lateinit var slider: Slider
    private lateinit var textTimer: TextView
    private lateinit var circleProgress: ProgressBar
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonStart = findViewById(R.id.button_start)
        buttonStop = findViewById(R.id.button_stop)
        slider = findViewById(R.id.my_slider)
        textTimer = findViewById(R.id.text_timer)
        circleProgress = findViewById(R.id.progressBarCircular)

        val toast = Toast.makeText(this, "Timer Task Finished", Toast.LENGTH_SHORT)

        slider.addOnChangeListener { _, _, _ ->
            counter = slider.value.toInt()
            textTimer.text = slider.value.toInt().toString()
        }

        binding.buttonStart.setOnClickListener {
            step = (MAX / slider.value.toInt())
            uiScope.launch {
                for (i in counter downTo 0 step 1) {
                    textTimer.text = "$i"
                    delay(1000)
                    circleProgress.progress -= step
                }
                circleProgress.progress = MAX
                viewUpdater()
                toast.show()
            }
            slider.isEnabled = false
            buttonStart.isVisible = false
            buttonStop.isVisible = true
        }

        binding.buttonStop.setOnClickListener {
            viewUpdater()
            slider.isEnabled = true
            buttonStart.isVisible = true
            buttonStop.isVisible = false
            toast.show()
            uiScope.coroutineContext.cancelChildren()
        }

    }

    private fun viewUpdater() {
        if (circleProgress.progress >= MIN) {
            circleProgress.progress = MAX
            textTimer.text = slider.value.toInt().toString()
        }
        slider.isEnabled = true
        buttonStart.isVisible = true
        buttonStop.isVisible = false
    }


}
