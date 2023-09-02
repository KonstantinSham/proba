package com.example.recycler.api


import com.example.recycler.models.PagedPhotosList
import com.example.recycler.models.Photos
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchMarsPhotosApi {
    @Headers("X-API-KEY: $api_key")
    @GET("api/v1/rovers/curiosity/photos?sol=1000")
    suspend fun getPhotosInfoList(): Photos

    @Headers("X-API-KEY: $api_key")
    @GET("api/v1/rovers/curiosity/photos?sol=1000")
    suspend fun topList(@Query("page") page: Int): PagedPhotosList

    private companion object {
        private const val api_key = "6mU4IxhAvbUcQskbxYR4cG5CsvzzjJNl153lNkkK"
    }
}

val retrofit: SearchMarsPhotosApi = Retrofit
    .Builder()
    .client(
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }).build()
    )
    .baseUrl("https://api.nasa.gov/mars-photos/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
    .create(SearchMarsPhotosApi::class.java)