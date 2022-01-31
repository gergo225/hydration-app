package com.gergo225.hydrationapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gergo225.hydrationapp.repository.database.HydrationDatabaseDao
import com.gergo225.hydrationapp.repository.preferences.UserPreferences

class HomeViewModelFactory(
    private val databaseDao: HydrationDatabaseDao,
    private val preferences: UserPreferences
) :
    ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(databaseDao, preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}