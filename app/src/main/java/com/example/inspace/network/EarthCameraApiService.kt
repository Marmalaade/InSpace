package com.example.inspace.network

import com.example.inspace.properties.EarthCameraDateProperty
import com.example.inspace.retrofitobject.RetrofitObjectInitialization
import retrofit2.http.GET

private const val BASE_URL = "https://api.nasa.gov/EPIC/api/"

interface EarthCameraApiService {
    @GET("natural/all?api_key=g6LnNorzWiIzqiTSmEbEell4AHdfz7w6dDfuOzvb")
    suspend fun getPropertiesAsync(): List<EarthCameraDateProperty>
}

object EarthCameraApi {
    val retrofitService: EarthCameraApiService by lazy {
        RetrofitObjectInitialization.initRetrofit(BASE_URL).create(EarthCameraApiService::class.java)
    }
}