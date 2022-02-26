package com.example.inspace.mainastronomicalpicture

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inspace.network.MainPictureApi
import com.example.inspace.network.MainPictureProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainPictureViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val _displayData = MutableLiveData<Pair<String, String>>()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val displayData: LiveData<Pair<String, String>>
        get() = _displayData

    init {
        getMainPictureData()
    }

    private fun getMainPictureData() {
        coroutineScope.launch {
            val getPropertiesDeferred = MainPictureApi.retrofitService.getPropertiesAsync()
            try {
                val result = getPropertiesDeferred.await()
                _displayData.value = Pair(result.title, result.img_url)

            } catch (t: Throwable) {
                Log.e("Failure", "${t.message}")
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
