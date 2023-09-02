package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var passengerInfo: TextView
    private lateinit var numberOfPassenger: TextView
    private lateinit var plusButton: ImageButton
    private lateinit var minusButton: ImageButton
    private lateinit var resetButton: Button
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        passengerInfo = findViewById(R.id.passengers_info)
        numberOfPassenger = findViewById(R.id.number_of_passengers)
        plusButton = findViewById(R.id.plus_button)
        minusButton = findViewById(R.id.minus_button)
        resetButton = findViewById(R.id.reset_button)

        binding.plusButton.setOnClickListener {
            counter++
            counter(counter)
            binding.numberOfPassengers.text = counter.toString()
        }
        binding.minusButton.setOnClickListener {
            if (counter >= 1) {
                counter--
                counter(counter)
                binding.numberOfPassengers.text = counter.toString()
            }
        }
        binding.resetButton.setOnClickListener {
            finish()
            startActivity(intent)
        }
    }


    @SuppressLint("SetTextI18n")
    private fun counter(counter: Int) {
        if (counter in 1..50) {
            passengerInfo.setTextColor(Color.BLUE)
            passengerInfo.text = "Осталось мест: ${50 - counter}"
            resetButton.visibility = View.INVISIBLE
            if (counter >= 50) {
                resetButton.visibility = View.VISIBLE
                passengerInfo.setTextColor(Color.RED)
                passengerInfo.setText(R.string.over_crowded_bus)
            }
        }
    }
}
