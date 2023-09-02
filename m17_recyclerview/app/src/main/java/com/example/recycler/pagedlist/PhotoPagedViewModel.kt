package com.example.recycler.pagedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recycler.models.Photo
import kotlinx.coroutines.flow.Flow


class PhotoPagedViewModel : ViewModel() {

    val pagedPhotos: Flow<PagingData<Photo>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { PhotoPagingSource() }
    ).flow.cachedIn(viewModelScope)

}