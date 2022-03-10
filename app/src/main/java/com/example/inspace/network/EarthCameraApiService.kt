package com.example.inspace.network

import com.example.inspace.properties.EarthCameraDateProperty
import com.example.inspace.properties.EarthCameraPhotoProperty
import com.example.inspace.retrofitobject.RetrofitObjectInitialization
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.nasa.gov/EPIC/api/"


interface EarthCameraApiService {

    object apiKey {
        const val KEY = "g6LnNorzWiIzqiTSmEbEell4AHdfz7w6dDfuOzvb"
    }

    @GET("natural/all?api_key=$KEY")
    suspend fun getPropertiesAsync(): List<EarthCameraDateProperty>

    @GET("natural/date/{date}?api_key=$KEY")
    suspend fun getPhotoListAsync(@Path("date") date: String): List<EarthCameraPhotoProperty>
}


object EarthCameraApi {
    val retrofitService: EarthCameraApiService by lazy {
        RetrofitObjectInitialization.initRetrofit(BASE_URL).create(EarthCameraApiService::class.java)
    }
}