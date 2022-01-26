package com.gergo225.hydrationapp.repository.database

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun dateFromTimestamp(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}