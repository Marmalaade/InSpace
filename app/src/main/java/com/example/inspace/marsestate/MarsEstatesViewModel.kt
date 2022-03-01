package com.example.inspace.marsestate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inspace.network.MarsApi
import com.example.inspace.network.MarsApiStatus
import com.example.inspace.properties.MarsProperty
import com.example.inspace.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MarsEstatesViewModel : ViewModel() {

    private val _status = MutableLiveData<MarsApiStatus>()
    private val _properties = MutableLiveData<List<MarsProperty>>()
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val status: LiveData<MarsApiStatus>
        get() = _status
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    init {
        getMarsEstateProperties()
    }

    private fun getMarsEstateProperties() {
        coroutineScope.launch {
            val getPropertiesDeferred = MarsApi.retrofitService.getPropertiesAsync()
            try {
                _status.value = MarsApiStatus.LOADING
                if (getPropertiesDeferred.isNotEmpty()) {
                    _status.value = MarsApiStatus.DONE
                    _properties.value = getPropertiesDeferred
                }
            } catch (t: Throwable) {
                t.printStackTrace()
                _properties.value = ArrayList()
                _status.value = MarsApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}