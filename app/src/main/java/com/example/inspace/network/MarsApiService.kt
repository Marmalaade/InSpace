package com.example.inspace.network

import com.example.inspace.retrofitobject.RetrofitObjectInitialization
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

private const val BASE_URL = "https://mars.udacity.com/"

interface MarsApiService {
    @GET("realestate")
    fun getPropertiesAsync(): Deferred<List<MarsProperty>>
}

object MarsApi {
    val retrofitService: MarsApiService by lazy {
        RetrofitObjectInitialization.initRetrofit(BASE_URL).create(MarsApiService::class.java)
    }
}
