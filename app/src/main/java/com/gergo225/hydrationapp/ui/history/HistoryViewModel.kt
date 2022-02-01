package com.gergo225.hydrationapp.ui.history

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gergo225.hydrationapp.repository.database.HydrationDatabaseDao
import com.gergo225.hydrationapp.repository.preferences.UserPreferences

class HistoryViewModel(val database: HydrationDatabaseDao, preferences: UserPreferences) :
    ViewModel() {

    private val last30DaysHydration = database.getLast30()
    private val hydrationGoal = preferences.hydrationGoalLiveData
    val last30DaysHydrationItems = Transformations.map(last30DaysHydration) { dailyHydrations ->
        dailyHydrations.map { (id, hydration, date) ->
            DailyHydrationItem(id, hydration, date, hydrationGoal.value ?: 2000)
        }
    }

}