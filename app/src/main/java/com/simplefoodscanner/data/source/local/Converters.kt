package com.simplefoodscanner.data.source.local

import androidx.room.TypeConverter
import java.time.LocalDateTime

object Converters {
    @TypeConverter
    fun stringToLocalDateTime(value: String?): LocalDateTime? = value?.let(LocalDateTime::parse)

    @TypeConverter
    fun localDateTimeToString(value: LocalDateTime?) = value?.toString()
}