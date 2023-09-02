package com.example.recycler.pagedlist

import com.example.recycler.api.retrofit
import com.example.recycler.models.Photo

class PhotoPagedListRepository {
    suspend fun getTopList(page: Int): List<Photo> {
        return retrofit.topList(page).photos
    }
}