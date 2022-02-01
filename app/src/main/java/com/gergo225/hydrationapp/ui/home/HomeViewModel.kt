package com.gergo225.hydrationapp.ui.home

import android.text.format.DateUtils
import androidx.lifecycle.*
import com.gergo225.hydrationapp.repository.database.DailyHydration
import com.gergo225.hydrationapp.repository.database.HydrationDatabaseDao
import com.gergo225.hydrationapp.repository.preferences.UserPreferences
import kotlinx.coroutines.launch

class HomeViewModel(val database: HydrationDatabaseDao, preferences: UserPreferences) :
    ViewModel() {

    val addHydrationAmount1 = preferences.container1LiveData
    val addHydrationAmount2 = preferences.container2LiveData
    val addHydrationAmount3 = preferences.container3LiveData
    val hydrationGoal = preferences.hydrationGoalLiveData

    private val dailyHydration = MutableLiveData<DailyHydration>()
    val hydrationProgress =
        Transformations.map(dailyHydration) { dailyHydration -> dailyHydration.hydrationMl }
    val hydrationProgressPercentage =
        Transformations.map(dailyHydration) { hydration -> Transformations.map(hydrationGoal) { goal -> hydration.hydrationMl * 100 / goal } }

    init {
        initializeDailyHydration()
    }

    private fun initializeDailyHydration() {
        viewModelScope.launch {
            dailyHydration.value = getDailyHydrationFromDatabase()
        }
    }

    private suspend fun getDailyHydrationFromDatabase(): DailyHydration {
        var hydration = database.getToday()

        if (hydration == null || !DateUtils.isToday(hydration.dayDate.time)) {
            val newDailyHydration = DailyHydration()
            database.insert(newDailyHydration)
            hydration = getDailyHydrationFromDatabase()
        }

        return hydration
    }

    fun addHydration(amount: Int) {
        viewModelScope.launch {
            val oldHydrationValue = dailyHydration.value ?: return@launch
            oldHydrationValue.hydrationMl = oldHydrationValue.hydrationMl.plus(amount)
            updateHydration(oldHydrationValue)
            dailyHydration.value = oldHydrationValue
        }
    }

    private suspend fun updateHydration(dailyHydration: DailyHydration) {
        database.update(dailyHydration)
    }

}