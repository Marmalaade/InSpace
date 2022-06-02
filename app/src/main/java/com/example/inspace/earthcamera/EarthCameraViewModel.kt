package com.example.inspace.earthcamera

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inspace.network.ApiStatus
import com.example.inspace.network.EarthCameraApi
import com.example.inspace.network.IsInternetAvailable
import com.example.inspace.properties.EarthCameraDateProperty
import com.example.inspace.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EarthCameraViewModel(application: Application) : AndroidViewModel(application) {

    private val _properties = MutableLiveData<List<EarthCameraDateProperty>>()
    private val _navigateToSelectedProperty = MutableLiveData<EarthCameraDateProperty?>()
    private val _status = MutableLiveData<ApiStatus>()
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    val properties: LiveData<List<EarthCameraDateProperty>>
        get() = _properties
    val status: LiveData<ApiStatus>
        get() = _status
    val navigateToSelectedProperty: LiveData<EarthCameraDateProperty?>
        get() = _navigateToSelectedProperty

    init {
        if (IsInternetAvailable.isConnected(context)) {
            getEarthCameraDates()
        }
    }

    private fun getEarthCameraDates() {
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