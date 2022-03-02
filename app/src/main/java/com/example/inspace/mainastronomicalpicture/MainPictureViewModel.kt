package com.example.inspace.mainastronomicalpicture

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inspace.network.MainPictureApi
import com.example.inspace.network.MarsApiStatus
import com.example.inspace.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainPictureViewModel : ViewModel() {

    private val _status = MutableLiveData<MarsApiStatus>()
    private val viewModelJob = Job()
    private val _displayData = MutableLiveData<Pair<String, String>?>()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val displayData: LiveData<Pair<String, String>?>
        get() = _displayData
    val status: LiveData<MarsApiStatus>
        get() = _status

    init {
        getMainPictureData()
    }

    private fun getMainPictureData() {
        coroutineScope.launch {
            _status.value = MarsApiStatus.LOADING
            val getPropertiesDeferred = MainPictureApi.retrofitService.getPropertiesAsync()
            try {
                _status.value = MarsApiStatus.DONE
                _displayData.value = Pair(getPropertiesDeferred.title, getPropertiesDeferred.img_url)

            } catch (e: Exception) {
                Log.e("Exception", e.message.toString())
                _status.value = MarsApiStatus.ERROR
                _displayData.value = null
            } catch (e: NoInternetException) {
                Log.e("NoInternetException", e.message.toString())
                _status.value = MarsApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
