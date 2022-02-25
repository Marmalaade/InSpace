package com.example.inspace.marsestate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inspace.network.MarsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MarsEstateViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val response: LiveData<String>
        get() = _response

    init {
        getMarsEstateProperties()
    }

    private fun getMarsEstateProperties() {
        coroutineScope.launch {
            val getPropertiesDeferred = MarsApi.retrofitService.getPropertiesAsync()
            try {
                val resultList = getPropertiesDeferred.await()
                _response.value = "${resultList.size}}"

            } catch (t: Throwable) {
                _response.value = "Failure" + t.message
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}