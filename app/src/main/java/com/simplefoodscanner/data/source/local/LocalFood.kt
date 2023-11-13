package com.simplefoodscanner.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "foods")
data class LocalFood(
    @PrimaryKey val code : String,
    val name: String,
    val brands: String,
    val nutriscore: String,
    val date: LocalDateTime
)