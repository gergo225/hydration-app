package com.gergo225.hydrationapp.repository.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "daily_hydration_table")
data class DailyHydration(
    @PrimaryKey(autoGenerate = true)
    var dayId: Long = 0L,
    @ColumnInfo(name = "hydration_ml")
    var hydrationMl: Int = 0,
    @ColumnInfo(name = "hydration_date")
    val dayDate: Date = Date(System.currentTimeMillis())
)