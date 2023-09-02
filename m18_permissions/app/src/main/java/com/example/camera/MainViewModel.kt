package com.example.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val photoDao: PhotoDao) : ViewModel() {

    fun onTakePhotoBtn(route: String) {
        viewModelScope.launch {
            photoDao.insert(
                Photo(route = route)
            )
        }
    }
}