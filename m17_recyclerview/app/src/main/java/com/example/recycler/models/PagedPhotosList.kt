package com.example.recycler.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PagedPhotosList(
    @Json(name = "photos") val photos: List<Photo>
)