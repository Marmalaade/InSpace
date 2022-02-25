package com.example.inspace.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.nasa.gov/planetary/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MainPictureApiService {
    @GET("apod?api_key=g6LnNorzWiIzqiTSmEbEell4AHdfz7w6dDfuOzvb")
    fun getProperties(): Call<MainPictureProperty>
}

object MainPictureApi {
    val retrofitService: MainPictureApiService by lazy {
        retrofit.create(MainPictureApiService::class.java)
    }
}