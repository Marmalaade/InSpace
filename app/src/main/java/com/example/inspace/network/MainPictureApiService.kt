package com.example.inspace.network

import com.example.inspace.properties.MainPictureProperty
import com.example.inspace.retrofitobject.RetrofitObjectInitialization
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

private const val BASE_URL = "https://api.nasa.gov/planetary/"

interface MainPictureApiService {
    @GET("apod?api_key=g6LnNorzWiIzqiTSmEbEell4AHdfz7w6dDfuOzvb")
    fun getPropertiesAsync(): Deferred<MainPictureProperty>
}

object MainPictureApi {
    val retrofitService: MainPictureApiService by lazy {
        RetrofitObjectInitialization.initRetrofit(BASE_URL).create(MainPictureApiService::class.java)
    }
}