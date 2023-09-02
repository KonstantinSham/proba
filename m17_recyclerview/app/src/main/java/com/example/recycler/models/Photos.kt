package com.example.recycler.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photos(
    @Json(name = "photos") val photos: List<Photo>
)

@JsonClass(generateAdapter = true)
data class Photo(
    @Json(name = "camera") val camera: Camera,
    @Json(name = "earth_date") val earth_date: String,
    @Json(name = "id") val id: Int,
    @Json(name = "img_src") val img_src: String,
    @Json(name = "rover") val rover: Rover,
    @Json(name = "sol") val sol: Int
)

@JsonClass(generateAdapter = true)
data class Camera(
    @Json(name = "full_name") val full_name: String,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "rover_id") val rover_id: Int
)

@JsonClass(generateAdapter = true)
data class Rover(
    @Json(name = "id") val id: Int,
    @Json(name = "landing_date") val landing_date: String,
    @Json(name = "launch_date") val launch_date: String,
    @Json(name = "name") val name: String,
    @Json(name = "status") val status: String
)

