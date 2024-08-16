package com.app.taskmanagement.data.database.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTimeConverter {
    @TypeConverter
    fun convertToString(value: LocalDateTime) = value.toString()

    @TypeConverter
    fun convertToLocalDateTime(value: String): LocalDateTime = LocalDateTime.parse(value)
}
