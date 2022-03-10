package com.example.inspace.earthcameraphoto

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inspace.properties.EarthCameraPhotoProperty

class EarthCameraPhotoViewModel(photoProperty: EarthCameraPhotoProperty, dateProperty: EarthCameraPhotoProperty, app: Application) :
    AndroidViewModel(app) {
    private val _selectedImage = MutableLiveData<EarthCameraPhotoProperty>()
    private val _selectedDate = MutableLiveData<EarthCameraPhotoProperty>()

    val selectedImage: LiveData<EarthCameraPhotoProperty>
        get() = _selectedImage

    val selectedDate: LiveData<EarthCameraPhotoProperty>
        get() = _selectedDate

    init {
        _selectedImage.value = photoProperty
        _selectedDate.value = dateProperty
    }
}