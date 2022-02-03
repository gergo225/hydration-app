package com.gergo225.hydrationapp.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gergo225.hydrationapp.repository.database.HydrationDatabaseDao
import com.gergo225.hydrationapp.repository.preferences.UserPreferences

class HistoryViewModel(val database: HydrationDatabaseDao, preferences: UserPreferences) :
    ViewModel() {

    private val last30DaysHydration = database.getLast30()
    private val hydrationGoal = preferences.hydrationGoalLiveData
    val last30DaysHydrationItems =
        Transformations.map(DoubleLiveData(last30DaysHydration, hydrationGoal)) {
            val hydration = it.first
            val goal = it.second
            hydration?.map { (id, amount, date) ->
                DailyHydrationItem(id, amount, date, goal ?: 2000)
            }
        }


}

private class DoubleLiveData<A, B>(a: LiveData<A>, b: LiveData<B>) :
    MediatorLiveData<Pair<A?, B?>>() {
    init {
        addSource(a) { value = it to b.value }
        addSource(b) { value = a.value to it }
    }
}
