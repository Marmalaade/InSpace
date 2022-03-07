package com.example.inspace.earthcamera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inspace.network.ApiStatus
import com.example.inspace.network.EarthCameraApi
import com.example.inspace.properties.EarthCameraDateProperty
import com.example.inspace.properties.MarsProperty
import com.example.inspace.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EarthCameraViewModel : ViewModel() {

    private val _properties = MutableLiveData<List<EarthCameraDateProperty>>()
    private val _navigateToSelectedProperty = MutableLiveData<EarthCameraDateProperty?>()
    private val _status = MutableLiveData<ApiStatus>()
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val properties: LiveData<List<EarthCameraDateProperty>>
        get() = _properties
    val status: LiveData<ApiStatus>
        get() = _status
    val navigateToSelectedProperty: LiveData<EarthCameraDateProperty?>
        get() = _navigateToSelectedProperty

    init {
        getEarthCameraPhotos()
    }

    private fun getEarthCameraPhotos() {
        coroutineScope.launch {
            _status.value = ApiStatus.LOADING
            val getPropertiesDeferred = EarthCameraApi.retrofitService.getPropertiesAsync()
            try {
                _status.value = ApiStatus.DONE
                _properties.value = getPropertiesDeferred

            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            } catch (e: NoInternetException) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun displayPhotoListProperty(earthCameraDateProperty: EarthCameraDateProperty) {
        _navigateToSelectedProperty.value = earthCameraDateProperty
    }

    fun displayPhotoListCompleted() {
        _navigateToSelectedProperty.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}