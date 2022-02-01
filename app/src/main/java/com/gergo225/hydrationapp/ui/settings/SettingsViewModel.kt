package com.gergo225.hydrationapp.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gergo225.hydrationapp.repository.preferences.UserPreferences

class SettingsViewModel(preferences: UserPreferences) : ViewModel() {

    val hydrationGoal = preferences.hydrationGoalLiveData
    val container1Size = preferences.container1LiveData
    val container2Size = preferences.container2LiveData
    val container3Size = preferences.container3LiveData

    private val _eventHydrationGoal = MutableLiveData<Boolean>()
    val eventHydrationGoal: LiveData<Boolean>
        get() = _eventHydrationGoal

    private val _eventContainer1 = MutableLiveData<Boolean>()
    val eventContainer1: LiveData<Boolean>
        get() = _eventContainer1

    private val _eventContainer2 = MutableLiveData<Boolean>()
    val eventContainer2: LiveData<Boolean>
        get() = _eventContainer2

    private val _eventContainer3 = MutableLiveData<Boolean>()
    val eventContainer3: LiveData<Boolean>
        get() = _eventContainer3

    fun onHydrationGoal() {
        _eventHydrationGoal.value = true
    }

    fun onHydrationGoalCompleted() {
        _eventHydrationGoal.value = false
    }

    fun onContainer1() {
        _eventContainer1.value = true
    }

    fun onContainer1Completed() {
        _eventContainer1.value = false
    }

    fun onContainer2() {
        _eventContainer2.value = true
    }

    fun onContainer2Completed() {
        _eventContainer2.value = false
    }

    fun onContainer3() {
        _eventContainer3.value = true
    }

    fun onContainer3Completed() {
        _eventContainer3.value = false
    }
}