package com.example.inspace.network

import com.example.inspace.properties.MainPictureProperty
import com.example.inspace.retrofitobject.RetrofitObjectInitialization
import retrofit2.http.GET

private const val BASE_URL = "https://api.nasa.gov/planetary/"
const val KEY = "g6LnNorzWiIzqiTSmEbEell4AHdfz7w6dDfuOzvb"

interface MainPictureApiService {
    @GET("apod?api_key=$KEY")
    suspend fun getPropertiesAsync(): MainPictureProperty
}

object MainPictureApi {
    val retrofitService: MainPictureApiService by lazy {
        RetrofitObjectInitialization.initRetrofit(BASE_URL).create(MainPictureApiService::class.java)
    }
}