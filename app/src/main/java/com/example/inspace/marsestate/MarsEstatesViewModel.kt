package com.example.inspace.marsestate

import android.util.Log
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
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty?>()
    val navigateToSelectedProperty: LiveData<MarsProperty?>
        get() = _navigateToSelectedProperty
    val status: LiveData<MarsApiStatus>
        get() = _status
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    init {
        getMarsEstateProperties()
    }

    private fun getMarsEstateProperties() {
        coroutineScope.launch {
            _status.value = MarsApiStatus.LOADING
            val getPropertiesDeferred = MarsApi.retrofitService.getPropertiesAsync()
            try {
                _status.value = MarsApiStatus.DONE
                _properties.value = getPropertiesDeferred

            } catch (e: Exception) {
                Log.e("Exception", e.message.toString())
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            } catch (e: NoInternetException) {
                _status.value = MarsApiStatus.ERROR
                Log.e("NoInternetException", e.message.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    fun displayDetailsCompleted() {
        _navigateToSelectedProperty.value = null
    }
}