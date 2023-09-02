package com.example.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val userDao: WordDao) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Success)
    val state = _state.asStateFlow()
    private fun isValid(text: String): Boolean {
        val regex = "^[A-Za-z-]*$".toRegex()
        return !text.matches(regex)
    }

    val allWords = this.userDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun onAddBtn(editText: String) {
//        val list = mutableListOf<String>()
        viewModelScope.launch {
            if (editText.isEmpty() || isValid(editText)) {
                _state.value = State.Error("Это слово невозможно добавить в словарь")
            } else {
                when (val findWord = allWords.value.find { it.word == editText }) {
                    null -> {
                        userDao.insert(
                            Word(
                                word = editText,
                                counts = 1
                            )
                        )
                    }
                    else -> {
                        var count = findWord.counts
                        count++
                        val current = findWord.copy(counts = count)
                        userDao.update(current)
                    }
                }
            }
//            _state.value = State.Success
//            if (editText.isEmpty() || isValid(editText)) {
//                _state.value = State.Error("Это слово невозможно добавить в словарь")
//            } else {
//                for (word in allWords.value) {
//                    if (allWords.value.isEmpty()) {
//                        userDao.insert(
//                            Word(
//                                word = editText,
//                                counts = 1
//                            )
//                        )
//                        break
//                    }
//                    var count = word.counts
//                    list.add(word.word)
//                    if (word.word == editText) {
//                        count++
//                        val current = word.copy(counts = count)
//                        userDao.update(current)
//                    }
//                }
//                if (editText !in list) {
//                    userDao.insert(
//                        Word(
//                            word = editText,
//                            counts = 1
//                        )
//                    )
//                }
//            }

        }
    }

    fun onDeleteBtn() {
        viewModelScope.launch {
            for (word in allWords.value) {
                userDao.delete(word)
            }
        }
    }
}