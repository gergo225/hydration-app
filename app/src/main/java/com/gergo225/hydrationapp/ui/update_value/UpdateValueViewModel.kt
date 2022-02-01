package com.gergo225.hydrationapp.ui.update_value

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gergo225.hydrationapp.repository.preferences.UserPreferences
import com.gergo225.hydrationapp.ui.settings.SettingsOption
import kotlinx.coroutines.launch

class UpdateValueViewModel(
    private val settingsOption: SettingsOption,
    val preferences: UserPreferences
) :
    ViewModel() {

    private val _eventFinish = MutableLiveData<Boolean>()
    val eventFinish: LiveData<Boolean>
        get() = _eventFinish

    fun onSubmitValue(newValue: Int) {
        when (settingsOption) {
            SettingsOption.GOAL -> updateHydration(newValue)
            SettingsOption.CONTAINER1 -> updateContainer1(newValue)
            SettingsOption.CONTAINER2 -> updateContainer2(newValue)
            SettingsOption.CONTAINER3 -> updateContainer3(newValue)
        }

        _eventFinish.value = true
    }

    fun onFinishEventCompleted() {
        _eventFinish.value = false
    }

    private fun updateHydration(newValue: Int) {
        viewModelScope.launch {
            preferences.updateHydrationGoal(newValue)
        }
    }

    private fun updateContainer1(newValue: Int) {
        viewModelScope.launch {
            preferences.updateContainer1(newValue)
        }
    }

    private fun updateContainer2(newValue: Int) {
        viewModelScope.launch {
            preferences.updateContainer2(newValue)
        }
    }

    private fun updateContainer3(newValue: Int) {
        viewModelScope.launch {
            preferences.updateContainer3(newValue)
        }
    }
}