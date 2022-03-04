package com.example.inspace.network

import com.example.inspace.properties.MarsProperty
import com.example.inspace.retrofitobject.RetrofitObjectInitialization
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

enum class MarsApiFilter(val filterValue: String) { SHOW_ALL("all"), SHOW_BYE("buy"), SHOW_RENT("rent") }

interface MarsApiService {
    @GET("realestate")
    suspend fun getPropertiesAsync(@Query("filter") type: String): List<MarsProperty>
}

object MarsApi {
    val retrofitService: MarsApiService by lazy {
        RetrofitObjectInitialization.initRetrofit(BASE_URL).create(MarsApiService::class.java)
    }
}
