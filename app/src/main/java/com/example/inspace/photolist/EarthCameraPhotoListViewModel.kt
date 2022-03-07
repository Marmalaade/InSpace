package com.example.inspace.photolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inspace.properties.EarthCameraDateProperty

class EarthCameraPhotoListViewModel(earthCameraDateProperty: EarthCameraDateProperty, app: Application) : AndroidViewModel(app) {

    private val _selectedProperty = MutableLiveData<EarthCameraDateProperty>()
    val selectedProperty: LiveData<EarthCameraDateProperty>
        get() = _selectedProperty

    init {
        _selectedProperty.value = earthCameraDateProperty
    }

}



