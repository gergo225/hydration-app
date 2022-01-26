package com.gergo225.hydrationapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

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

    private val _hydrationProgress = MutableLiveData<Int>()
    val hydrationProgress: LiveData<Int>
        get() = _hydrationProgress

    val hydrationProgressPercentage =
        Transformations.map(hydrationProgress) { progress -> Transformations.map(hydrationGoal) { goal -> progress * 100 / goal } }

    init {
        _addHydrationAmount1.value = 200
        _addHydrationAmount2.value = 400
        _addHydrationAmount3.value = 500
        _hydrationGoal.value = 2000
        _hydrationProgress.value = 0
    }

    fun addHydration(amount: Int) {
        _hydrationProgress.value = _hydrationProgress.value?.plus(amount)
    }

}