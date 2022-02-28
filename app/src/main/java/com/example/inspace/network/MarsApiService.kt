package com.example.inspace.network

import com.example.inspace.properties.MarsProperty
import com.example.inspace.retrofitobject.RetrofitObjectInitialization
import retrofit2.http.GET

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

interface MarsApiService {
    @GET("realestate")
    suspend fun getPropertiesAsync(): List<MarsProperty>
}

object MarsApi {
    val retrofitService: MarsApiService by lazy {
        RetrofitObjectInitialization.initRetrofit(BASE_URL).create(MarsApiService::class.java)
    }
}
