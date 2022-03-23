package com.example.inspace.mainastronomicalpicture

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inspace.database.getDatabase
import com.example.inspace.network.ApiStatus
import com.example.inspace.repository.MainPictureRepository
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
