package com.gergo225.hydrationapp.repository.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.gergo225.hydrationapp.R
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_NAME = "user_preferences"
val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

class UserPreferences(private val context: Context) {

    companion object {
        val HYDRATION_GOAL_KEY = intPreferencesKey(name = "hydration_goal")
        val CONTAINER1_SIZE_KEY = intPreferencesKey(name = "container1_size")
        val CONTAINER2_SIZE_KEY = intPreferencesKey(name = "container2_size")
        val CONTAINER3_SIZE_KEY = intPreferencesKey(name = "container3_size")
    }

    val hydrationGoalLiveData: LiveData<Int> = context.dataStore.data.map { preferences ->
        preferences[HYDRATION_GOAL_KEY]
            ?: context.resources.getInteger(R.integer.hydration_goal_default) // default is 2000 (unless users sets a new value)
    }.distinctUntilChanged().asLiveData()

    val container1LiveData: LiveData<Int> = context.dataStore.data.map { preferences ->
        preferences[CONTAINER1_SIZE_KEY]
            ?: context.resources.getInteger(R.integer.container1_size_default)
    }.distinctUntilChanged().asLiveData()

    val container2LiveData: LiveData<Int> = context.dataStore.data.map { preferences ->
        preferences[CONTAINER2_SIZE_KEY]
            ?: context.resources.getInteger(R.integer.container2_size_default)
    }.distinctUntilChanged().asLiveData()

    val container3LiveData: LiveData<Int> = context.dataStore.data.map { preferences ->
        preferences[CONTAINER3_SIZE_KEY]
            ?: context.resources.getInteger(R.integer.container3_size_default)
    }.distinctUntilChanged().asLiveData()


    suspend fun updateHydrationGoal(goal: Int) {
        context.dataStore.edit { preferences ->
            preferences[HYDRATION_GOAL_KEY] = goal
        }
    }

    suspend fun updateContainer1(newAmount: Int) {
        context.dataStore.edit { preferences ->
            preferences[CONTAINER1_SIZE_KEY] = newAmount
        }
    }

    suspend fun updateContainer2(newAmount: Int) {
        context.dataStore.edit { preferences ->
            preferences[CONTAINER2_SIZE_KEY] = newAmount
        }
    }

    suspend fun updateContainer3(newAmount: Int) {
        context.dataStore.edit { preferences ->
            preferences[CONTAINER3_SIZE_KEY] = newAmount
        }
    }

}