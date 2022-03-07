package com.example.inspace.photolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inspace.network.ApiStatus
import com.example.inspace.properties.EarthCameraDateProperty

class EarthCameraPhotoListViewModel(earthCameraDateProperty: EarthCameraDateProperty, app: Application) : AndroidViewModel(app) {

    private val _status = MutableLiveData<ApiStatus>()
    private val _selectedProperty = MutableLiveData<EarthCameraDateProperty>()

    val status: LiveData<ApiStatus>
        get() = _status
    val selectedProperty: LiveData<EarthCameraDateProperty>
        get() = _selectedProperty

    init {
        _selectedProperty.value = earthCameraDateProperty
    }

}



