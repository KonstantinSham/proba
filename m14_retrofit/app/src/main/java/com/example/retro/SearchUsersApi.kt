package com.example.retro

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://randomuser.me"

object RetrofitServices{
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val searchUsersApi: SearchUsersApi = retrofit.create(SearchUsersApi::class.java)
}
interface SearchUsersApi {
    @GET("/api/")
    suspend fun getUsersInfoList():User
}