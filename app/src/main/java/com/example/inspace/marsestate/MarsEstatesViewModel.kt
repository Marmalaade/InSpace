package com.example.inspace.marsestate

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inspace.network.ApiStatus
import com.example.inspace.network.IsInternetAvailable
import com.example.inspace.network.MarsApi
import com.example.inspace.network.MarsApiFilter
import com.example.inspace.properties.MarsProperty
import com.example.inspace.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MarsEstatesViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<ApiStatus>()
    private val _properties = MutableLiveData<List<MarsProperty>>()
    private val viewModelJob = Job()

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty?>()
    val navigateToSelectedProperty: LiveData<MarsProperty?>
        get() = _navigateToSelectedProperty
    val status: LiveData<ApiStatus>
        get() = _status
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    init {
        if (IsInternetAvailable.isConnected(context)) {
            getMarsEstateProperties(MarsApiFilter.SHOW_ALL)
        }
    }

    private fun getMarsEstateProperties(filter: MarsApiFilter) {
        coroutineScope.launch {
            _status.value = ApiStatus.LOADING
            val getPropertiesDeferred = MarsApi.retrofitService.getPropertiesAsync(filter.filterValue)
            try {
                _status.value = ApiStatus.DONE
                _properties.value = getPropertiesDeferred

            } catch (e: Exception) {
                Log.e("Exception", e.message.toString())
                _status.value = ApiStatus.ERROR
                _properties.value = ArrayList()
            } catch (e: NoInternetException) {
                _status.value = ApiStatus.ERROR
                Log.e("NoInternetException", e.message.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    fun displayDetailsCompleted() {
        _navigateToSelectedProperty.value = null
    }

    fun updateFilter(filter: MarsApiFilter) {
        getMarsEstateProperties(filter)
    }

}