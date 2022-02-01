package com.gergo225.hydrationapp.ui.update_value

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gergo225.hydrationapp.repository.preferences.UserPreferences
import com.gergo225.hydrationapp.ui.settings.SettingsOption

class UpdateValueViewModelFactory(
    private val settingsOption: SettingsOption,
    private val preferences: UserPreferences
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateValueViewModel::class.java)) {
            return UpdateValueViewModel(settingsOption, preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}