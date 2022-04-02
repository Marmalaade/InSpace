package com.example.inspace.mainastronomicalpicture

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inspace.database.getDatabase
import com.example.inspace.network.ApiStatus
import com.example.inspace.network.MainPictureApi
import com.example.inspace.network.MarsApi
import com.example.inspace.network.MarsApiFilter
import com.example.inspace.properties.MainPictureProperty
import com.example.inspace.repository.MainPictureRepository
import com.example.inspace.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainPictureViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<ApiStatus>()
    private val viewModelJob = Job()

    private val database = getDatabase(application)
    private val mainPictureRepository = MainPictureRepository(database)

    val status: LiveData<ApiStatus>
        get() = _status


    init {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            mainPictureRepository.refreshMainScreenItems()
        }
        _status.value = ApiStatus.DONE
    }

    val mainItems = mainPictureRepository.mainItems

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
