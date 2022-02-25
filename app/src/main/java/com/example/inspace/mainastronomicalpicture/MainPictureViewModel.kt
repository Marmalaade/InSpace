package com.example.inspace.mainastronomicalpicture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inspace.network.MainPictureApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainPictureViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val response: LiveData<String>
        get() = _response

    init {
        getMainPictureData()
    }

    private fun getMainPictureData() {
        coroutineScope.launch {
            val getPropertiesDeferred = MainPictureApi.retrofitService.getPropertiesAsync()
            try {
                val result = getPropertiesDeferred.await()
                _response.value = result.title
            } catch (t: Throwable) {
                _response.value = "Failure:" + t.message
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
