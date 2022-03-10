package com.example.inspace.earthcameraphoto

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inspace.properties.EarthCameraPhotoProperty

class EarthCameraPhotoViewModelFactory(
    private val earthCameraPhotoProperty: EarthCameraPhotoProperty,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EarthCameraPhotoViewModel::class.java)) {
            return EarthCameraPhotoViewModel(earthCameraPhotoProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}