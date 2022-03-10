package com.example.inspace.earthcameraphotolist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inspace.properties.EarthCameraDateProperty

class EarthCameraPhotoListViewModelFactory(
    private val earthCameraDateProperty: EarthCameraDateProperty,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EarthCameraPhotoListViewModel::class.java)) {
            return EarthCameraPhotoListViewModel(earthCameraDateProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}