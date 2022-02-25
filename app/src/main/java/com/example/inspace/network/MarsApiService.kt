package com.example.inspace.network

import com.example.inspace.retrofitobject.RetrofitObjectInitialization
import retrofit2.Call
import retrofit2.http.GET

private const val BASE_URL = "https://mars.udacity.com/"

interface MarsApiService {
    @GET("realestate")
    fun getProperties(): Call<List<MarsProperty>>
}

object MarsApi {
    val retrofitService: MarsApiService by lazy {
        RetrofitObjectInitialization.initRetrofit(BASE_URL).create(MarsApiService::class.java)
    }
}
