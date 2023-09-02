package com.example.room

sealed class State {
    object Success : State()
    data class Error(val message: String) : State()
}
