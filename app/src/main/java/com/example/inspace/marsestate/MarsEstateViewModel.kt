package com.example.inspace.marsestate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inspace.network.MarsApi
import com.example.inspace.properties.MarsPropertyItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MarsEstateViewModel : ViewModel() {

    private val _property = MutableLiveData<MarsPropertyItem>()
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val property: LiveData<MarsPropertyItem>
        get() = _property

    init {
        getMarsEstateProperties()
    }

    private fun getMarsEstateProperties() {
        coroutineScope.launch {
            val getPropertiesDeferred = MarsApi.retrofitService.getPropertiesAsync()
            try {
                if (getPropertiesDeferred.isNotEmpty()) {
                    _property.value = getPropertiesDeferred[0]
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}