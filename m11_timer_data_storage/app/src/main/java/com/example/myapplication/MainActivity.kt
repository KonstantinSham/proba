package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
         val repository = Repository(this)
        val getText = repository.getText()
        binding.textView.text = getText
        val saveText = binding.editText.text




        binding.buttonSave.setOnClickListener {

            repository.saveText(saveText.toString())
            binding.textView.text = saveText

        }
        binding.buttonClear.setOnClickListener {
            binding.textView.text = null
            repository.clearText()
        }
    }
}