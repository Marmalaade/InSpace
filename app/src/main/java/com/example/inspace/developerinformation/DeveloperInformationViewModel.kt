package com.example.inspace.developerinformation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DeveloperInformationViewModel(application: Application) : AndroidViewModel(application) {

    private val _onInstagramCardViewClickEvent = MutableLiveData(true)
    private val _onTelegramCardViewClickEvent = MutableLiveData(true)
    private val _onDiscordCardViewClickEvent = MutableLiveData(true)

    val onInstagramCardViewClickEvent: LiveData<Boolean>
        get() = _onInstagramCardViewClickEvent
    val onTelegramCardViewClickEvent: LiveData<Boolean>
        get() = _onTelegramCardViewClickEvent
    val onDiscordCardViewClickEvent: LiveData<Boolean>
        get() = _onDiscordCardViewClickEvent

    fun onInstagramCardViewCLick() {
        _onInstagramCardViewClickEvent.value = false
    }

    fun onTelegramCardViewCLick() {
        _onTelegramCardViewClickEvent.value = false
    }

    fun onDiscordCardViewCLick() {
        _onDiscordCardViewClickEvent.value = false
    }


}

