package com.example.inspace.mainastronomicalpicture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inspace.network.MainPictureApi
import com.example.inspace.network.MainPictureProperty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPictureViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    init {
        getMainPictureData()
    }

    private fun getMainPictureData() {
        MainPictureApi.retrofitService.getProperties().enqueue(object : Callback<MainPictureProperty> {
            override fun onFailure(call: Call<MainPictureProperty>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<MainPictureProperty>, response: Response<MainPictureProperty>) {
                _response.value = response.body()?.title
            }
        })
    }
}
