package com.example.yandexnav.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://search-maps.yandex.ru"
object OpenStreetMapApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val searchCoordinatesApi: SearchCoordinatesApi = retrofit.create(SearchCoordinatesApi::class.java)
}

interface SearchCoordinatesApi{
    @GET("/v1/")
    suspend fun getPlacesByCoordinates() : Place
}