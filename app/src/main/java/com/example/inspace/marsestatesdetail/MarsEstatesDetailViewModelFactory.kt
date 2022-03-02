package com.example.inspace.marsestatesdetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inspace.properties.MarsProperty

class MarsEstatesDetailViewModelFactory(
    private val marsProperty: MarsProperty,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarsEstatesDetailViewMode::class.java)) {
            return MarsEstatesDetailViewMode(marsProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}