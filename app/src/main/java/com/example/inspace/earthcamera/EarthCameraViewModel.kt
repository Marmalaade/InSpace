package com.example.inspace.earthcamera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inspace.network.ApiStatus
import com.example.inspace.network.EarthCameraApi
import com.example.inspace.properties.EarthCameraDateProperty
import com.example.inspace.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EarthCameraViewModel : ViewModel() {

    private val _properties = MutableLiveData<EarthCameraDateProperty>()
    private val _status = MutableLiveData<ApiStatus>()
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val properties: LiveData<EarthCameraDateProperty>
        get() = _properties

    init {
        getEarthCameraPhotos()
    }

    private fun getEarthCameraPhotos() {
        coroutineScope.launch {
            _status.value = ApiStatus.LOADING
            val getPropertiesDeferred = EarthCameraApi.retrofitService.getPropertiesAsync()
            try {
                _status.value = ApiStatus.DONE
                if(getPropertiesDeferred.isNotEmpty()){
                    _properties.value = getPropertiesDeferred[0]
                }

            } catch (e: Exception) {
                Log.e("Exception", e.message.toString())
                _status.value = ApiStatus.ERROR
            } catch (e: NoInternetException) {
                _status.value = ApiStatus.ERROR
                Log.e("NoInternetException", e.message.toString())
            }
        }
    }
}