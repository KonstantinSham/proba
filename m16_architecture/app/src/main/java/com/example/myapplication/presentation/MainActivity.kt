package com.example.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private val viewModel: MainViewModel by viewModels { mainViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.reloadUsefulActivity()

        binding.resetBtn.setOnClickListener {
            viewModel.reloadUsefulActivity()
        }

        lifecycleScope.launch {
            viewModel.state.collect {
                binding.textView.text = it
            }
        }
    }
}