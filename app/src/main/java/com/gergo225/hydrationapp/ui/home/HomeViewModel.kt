package com.gergo225.hydrationapp.ui.home

import android.text.format.DateUtils
import androidx.lifecycle.*
import com.gergo225.hydrationapp.repository.database.DailyHydration
import com.gergo225.hydrationapp.repository.database.HydrationDatabaseDao
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(val database: HydrationDatabaseDao) : ViewModel() {

    private val _addHydrationAmount1 = MutableLiveData<Int>()
    val addHydrationAmount1: LiveData<Int>
        get() = _addHydrationAmount1

    private val _addHydrationAmount2 = MutableLiveData<Int>()
    val addHydrationAmount2: LiveData<Int>
        get() = _addHydrationAmount2

    private val _addHydrationAmount3 = MutableLiveData<Int>()
    val addHydrationAmount3: LiveData<Int>
        get() = _addHydrationAmount3

    private val _hydrationGoal = MutableLiveData<Int>()
    val hydrationGoal: LiveData<Int>
        get() = _hydrationGoal

    private val dailyHydration = MutableLiveData<DailyHydration>()

    val hydrationProgress =
        Transformations.map(dailyHydration) { dailyHydration -> dailyHydration.hydrationMl }

    val hydrationProgressPercentage =
        Transformations.map(dailyHydration) { hydration -> Transformations.map(hydrationGoal) { goal -> hydration.hydrationMl * 100 / goal } }

    init {
        _addHydrationAmount1.value = 200
        _addHydrationAmount2.value = 400
        _addHydrationAmount3.value = 500
        _hydrationGoal.value = 2000
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