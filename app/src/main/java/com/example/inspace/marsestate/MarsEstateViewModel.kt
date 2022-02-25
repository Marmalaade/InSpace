package com.example.inspace.marsestate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inspace.network.MarsApi
import com.example.inspace.network.MarsProperty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsEstateViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    init {
        getMarsEstateProperties()
    }

    private fun getMarsEstateProperties() {
        MarsApi.retrofitService.getProperties().enqueue(object : Callback<List<MarsProperty>> {
            override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<List<MarsProperty>>, response: Response<List<MarsProperty>>) {
                _response.value = "${response.body()?.size}"
            }
        })
    }
}