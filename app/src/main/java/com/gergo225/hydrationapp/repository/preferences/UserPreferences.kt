package com.gergo225.hydrationapp.repository.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_NAME = "user_preferences"
val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

class UserPreferences(private val context: Context) {

    companion object {
        val HYDRATION_GOAL_KEY = intPreferencesKey(name = "hydration_goal")
        val CONTAINER_1_KEY = intPreferencesKey(name = "container1")
        val CONTAINER_2_KEY = intPreferencesKey(name = "container2")
        val CONTAINER_3_KEY = intPreferencesKey(name = "container3")

        const val DEFAULT_GOAL = 2000
        const val DEFAULT_CONTAINER1 = 200
        const val DEFAULT_CONTAINER2 = 400
        const val DEFAULT_CONTAINER3 = 500
    }

    val hydrationGoalLiveData: LiveData<Int> = context.dataStore.data.map { preferences ->
        preferences[HYDRATION_GOAL_KEY]
            ?: DEFAULT_GOAL // default is 2000 (unless users sets a new value)
    }.distinctUntilChanged().asLiveData()

    val container1LiveData: LiveData<Int> = context.dataStore.data.map { preferences ->
        preferences[CONTAINER_1_KEY] ?: DEFAULT_CONTAINER1
    }.distinctUntilChanged().asLiveData()

    val container2LiveData: LiveData<Int> = context.dataStore.data.map { preferences ->
        preferences[CONTAINER_2_KEY] ?: DEFAULT_CONTAINER2
    }.distinctUntilChanged().asLiveData()

    val container3LiveData: LiveData<Int> = context.dataStore.data.map { preferences ->
        preferences[CONTAINER_3_KEY] ?: DEFAULT_CONTAINER3
    }.distinctUntilChanged().asLiveData()


    suspend fun updateHydrationGoal(goal: Int) {
        context.dataStore.edit { preferences ->
            preferences[HYDRATION_GOAL_KEY] = goal
        }
    }

    suspend fun updateContainer1(newAmount: Int) {
        context.dataStore.edit { preferences ->
            preferences[CONTAINER_1_KEY] = newAmount
        }
    }

    suspend fun updateContainer2(newAmount: Int) {
        context.dataStore.edit { preferences ->
            preferences[CONTAINER_2_KEY] = newAmount
        }
    }

    suspend fun updateContainer3(newAmount: Int) {
        context.dataStore.edit { preferences ->
            preferences[CONTAINER_3_KEY] = newAmount
        }
    }

}