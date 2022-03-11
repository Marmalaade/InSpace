package com.example.inspace.earthcameraphoto

import android.app.Application
import android.graphics.Bitmap
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.example.inspace.network.ApiStatus
import com.example.inspace.properties.EarthCameraPhotoProperty
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import java.io.IOException

class EarthCameraPhotoViewModel(imageProperties: EarthCameraPhotoProperty, app: Application) :
    AndroidViewModel(app) {
    private val _status = MutableLiveData<ApiStatus>()
    private val _selectedImageProperties = MutableLiveData<EarthCameraPhotoProperty>()

    val status: LiveData<ApiStatus>
        get() = _status
    val selectedImageProperties: LiveData<EarthCameraPhotoProperty>
        get() = _selectedImageProperties

    init {
        _selectedImageProperties.value = imageProperties

    }


}