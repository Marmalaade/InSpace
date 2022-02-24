package com.example.inspace.marsestate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MarsEstateViewModel:ViewModel() {

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    init {
        getMarsEstateProperties()
    }

    private fun getMarsEstateProperties() {
        _response.value = "Your response will display here"
    }
}