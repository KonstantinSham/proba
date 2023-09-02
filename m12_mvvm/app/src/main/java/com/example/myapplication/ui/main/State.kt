package com.example.myapplication.ui.main

sealed class State {
    object Loading : State()
    object Success : State()
    data class Error(val message: String?) : State()
    data class EnterText(val msg: String?): State()
}
