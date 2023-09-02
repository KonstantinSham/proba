package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val wordDao = (application as App).db.wordDao()
                return MainViewModel(wordDao) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener { viewModel.onAddBtn(binding.editText.text.toString()) }
        binding.delbtn.setOnClickListener { viewModel.onDeleteBtn() }

        lifecycleScope.launchWhenCreated {
            viewModel.allWords.collect { words ->
                binding.textView.text = words.joinToString(separator = "\r\n")
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    State.Success -> {
                        binding.editText.error = null
                    }
                    is State.Error -> binding.editText.error = state.message
                }
            }
        }
    }
}