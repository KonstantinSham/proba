package com.example.myapplication.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Success)
    val state = _state.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    private val _text = Channel<String>()
    val getText = _text.receiveAsFlow()

    fun searchOnClick(enterText: String) {
        viewModelScope.launch {
            val stringSize = enterText.length
            var sizeError: String? = null
            if (stringSize in 0..3) {
                if (stringSize == 0){
                    sizeError = "Поле запроса пусто, введите Ваш запрос"
                }
                _state.value = State.Error(sizeError)
                _error.send("Ваш запрос менее 3 символов")
            } else {
                _state.value = State.Loading
                delay(5000)
                _state.value = State.Success
                _state.value = State.EnterText(enterText)
            }
        }
    }
}