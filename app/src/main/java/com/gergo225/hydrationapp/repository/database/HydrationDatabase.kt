package com.gergo225.hydrationapp.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [DailyHydration::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class HydrationDatabase : RoomDatabase() {
    abstract val hydrationDatabaseDao: HydrationDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: HydrationDatabase? = null

        fun getInstance(context: Context): HydrationDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HydrationDatabase::class.java,
                        "hydration_history_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}