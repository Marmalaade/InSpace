package com.example.inspace.earthcameraphoto

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inspace.properties.EarthCameraPhotoProperty

class EarthCameraPhotoViewModel(imageProperties: EarthCameraPhotoProperty, app: Application) :
    AndroidViewModel(app) {
    private val _selectedImageProperties = MutableLiveData<EarthCameraPhotoProperty>()

    val selectedImageProperties: LiveData<EarthCameraPhotoProperty>
        get() = _selectedImageProperties


    init {
        _selectedImageProperties.value = imageProperties
    }
}