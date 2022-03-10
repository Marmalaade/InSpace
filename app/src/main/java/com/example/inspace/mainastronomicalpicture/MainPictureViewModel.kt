package com.example.inspace.mainastronomicalpicture

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inspace.network.MainPictureApi
import com.example.inspace.network.ApiStatus
import com.example.inspace.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainPictureViewModel : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    private val viewModelJob = Job()
    private val _displayData = MutableLiveData<Pair<String, String>?>()
    val mama = "https://api.nasa.gov/EPIC/archive/natural/2019/05/30/png/epic_1b_20190530011359.png?api_key=g6LnNorzWiIzqiTSmEbEell4AHdfz7w6dDfuOzvb"
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val displayData: LiveData<Pair<String, String>?>
        get() = _displayData
    val status: LiveData<ApiStatus>
        get() = _status

    init {
        getMainPictureData()
    }

    private fun getMainPictureData() {
        coroutineScope.launch {
            _status.value = ApiStatus.LOADING
            val getPropertiesDeferred = MainPictureApi.retrofitService.getPropertiesAsync()
            try {
                _status.value = ApiStatus.DONE
                _displayData.value = Pair(getPropertiesDeferred.title, getPropertiesDeferred.img_url)

            } catch (e: Exception) {
                Log.e("Exception", e.message.toString())
                _status.value = ApiStatus.ERROR
                _displayData.value = null
            } catch (e: NoInternetException) {
                Log.e("NoInternetException", e.message.toString())
                _status.value = ApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
