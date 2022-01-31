package com.gergo225.hydrationapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {
    private val _eventSettings = MutableLiveData<Boolean>()
    val eventSettings: LiveData<Boolean>
        get() = _eventSettings

    fun onSettingsEvent() {
        _eventSettings.value = true
    }

    fun onSettingsEventCompleted() {
        _eventSettings.value = false
    }

}