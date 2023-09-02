package com.example.mycustomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mycustomview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.customView.setText("Верхняя строчка","Нижняя строчка")
    }
}