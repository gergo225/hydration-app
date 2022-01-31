package com.gergo225.hydrationapp.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface HydrationDatabaseDao {

    @Insert
    suspend fun insert(hydration: DailyHydration)

    @Update
    suspend fun update(hydration: DailyHydration)

    @Query("SELECT * FROM daily_hydration_table ORDER BY dayId DESC LIMIT 1")
    suspend fun getToday(): DailyHydration?

    @Query("SELECT * FROM daily_hydration_table ORDER BY hydration_date DESC LIMIT 30")
    fun getLast30(): LiveData<List<DailyHydration>>
}