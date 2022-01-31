package com.gergo225.hydrationapp.ui.settings

import androidx.lifecycle.ViewModel
import com.gergo225.hydrationapp.repository.preferences.UserPreferences

class SettingsViewModel(preferences: UserPreferences) : ViewModel() {

    val hydrationGoal = preferences.hydrationGoalLiveData
    val container1Size = preferences.container1LiveData
    val container2Size = preferences.container2LiveData
    val container3Size = preferences.container3LiveData

}