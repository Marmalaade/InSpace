package com.example.inspace.earthcameraphotolist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inspace.network.ApiStatus
import com.example.inspace.network.EarthCameraApi
import com.example.inspace.properties.EarthCameraDateProperty
import com.example.inspace.properties.EarthCameraPhotoProperty
import com.example.inspace.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EarthCameraPhotoListViewModel(earthCameraDateProperty: EarthCameraDateProperty, app: Application) : AndroidViewModel(app) {

    private val _status = MutableLiveData<ApiStatus>()
    private val _selectedProperty = MutableLiveData<EarthCameraDateProperty>()
    private val _navigateToSelectedImage = MutableLiveData<EarthCameraPhotoProperty?>()
    private val _properties = MutableLiveData<List<EarthCameraPhotoProperty>>()
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val navigateToSelectedImage: LiveData<EarthCameraPhotoProperty?>
        get() = _navigateToSelectedImage
    val properties: LiveData<List<EarthCameraPhotoProperty>>
        get() = _properties
    val status: LiveData<ApiStatus>
        get() = _status

    init {
        _selectedProperty.value = earthCameraDateProperty
        getEarthCameraPhotoList(_selectedProperty.value!!.date)
    }

    private fun getEarthCameraPhotoList(data: String) {
        coroutineScope.launch {
            _status.value = ApiStatus.LOADING
            val getPropertiesDeferred = EarthCameraApi.retrofitService.getPhotoListAsync(data)
            try {
                _status.value = ApiStatus.DONE
                val result = getPropertiesDeferred
                Log.e("mama", "$result")
                _properties.value = getPropertiesDeferred

            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            } catch (e: NoInternetException) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun displaySelectedImage(earthCameraPhotoProperty: EarthCameraPhotoProperty) {
        _navigateToSelectedImage.value = earthCameraPhotoProperty
    }

    fun displaySelectedImageCompleted() {
        _navigateToSelectedImage.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}



