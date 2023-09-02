package com.example.yandexnav.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Place(
    @Json(name = "name") val name: String,
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
    @Json(name = "address") val address: String,
    @Json(name = "description") val description: String,
    @Json(name = "rating") val rating: Double,
    @Json(name = "imageURL") val imageURL: String
)
