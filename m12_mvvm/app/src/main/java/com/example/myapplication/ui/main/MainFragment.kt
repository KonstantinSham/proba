package com.example.myapplication.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchButton.setOnClickListener {
            val enterString = binding.editText.text.toString()

            viewModel.searchOnClick(enterString)
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect() { state ->
                when (state) {
                    State.Loading -> {
                        binding.progress.isVisible = true
                        binding.editText.error = null
                    }
                    State.Success -> {
                        binding.progress.isVisible = false
                        binding.editText.error = null
                    }
                    is State.Error -> {
                        binding.progress.isVisible = false
                        binding.editText.error = state.message
                    }
                    is State.EnterText -> {
                        binding.progress.isVisible = false
                        binding.editText.error = null
                        binding.textView.text = "По запросу <${state.msg}> ничего не найдено"
                    }
                }
            }

        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.error.collect{message->
                Snackbar.make(requireView(),message,Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}