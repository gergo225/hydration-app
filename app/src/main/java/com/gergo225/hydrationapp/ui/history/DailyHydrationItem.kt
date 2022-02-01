package com.gergo225.hydrationapp.ui.history

import java.util.*

data class DailyHydrationItem(
    val dayId: Long,
    val hydrationMl: Int,
    val dayDate: Date,
    val hydrationGoal: Int
)