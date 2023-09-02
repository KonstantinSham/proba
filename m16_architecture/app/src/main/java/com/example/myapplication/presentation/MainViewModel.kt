package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.GetUsefulActivityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUsefulActivityUseCase: GetUsefulActivityUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<String>("")
    val state = _state.asStateFlow()

    fun reloadUsefulActivity() {
        viewModelScope.launch {
            _state.value = getUsefulActivityUseCase.execute().activity
        }
    }
}