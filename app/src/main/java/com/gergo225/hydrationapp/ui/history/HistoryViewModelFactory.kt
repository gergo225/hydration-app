package com.gergo225.hydrationapp.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gergo225.hydrationapp.repository.database.HydrationDatabaseDao
import com.gergo225.hydrationapp.repository.preferences.UserPreferences

class HistoryViewModelFactory(
    private val databaseDao: HydrationDatabaseDao,
    private val preferences: UserPreferences
) :
    ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(databaseDao, preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}