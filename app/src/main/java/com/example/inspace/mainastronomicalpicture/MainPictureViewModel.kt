package com.example.inspace.mainastronomicalpicture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inspace.network.MainPictureApi
import com.example.inspace.network.MarsApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainPictureViewModel : ViewModel() {

    private val _status = MutableLiveData<MarsApiStatus>()
    private val viewModelJob = Job()
    private val _displayData = MutableLiveData<Pair<String, String>>()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val displayData: LiveData<Pair<String, String>>
        get() = _displayData
    val status: LiveData<MarsApiStatus>
        get() = _status

    init {
        getMainPictureData()
    }

    private fun getMainPictureData() {
        coroutineScope.launch {
            val getPropertiesDeferred = MainPictureApi.retrofitService.getPropertiesAsync()
            try {
                _status.value = MarsApiStatus.LOADING
                val resultData = Pair(getPropertiesDeferred.title, getPropertiesDeferred.img_url)
                _status.value = MarsApiStatus.DONE
                _displayData.value = resultData

            } catch (t: Throwable) {
                _status.value = MarsApiStatus.ERROR
                t.printStackTrace()
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
