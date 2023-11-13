package com.simplefoodscanner.data.source.local

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "quantities",
        primaryKeys = ["foodCode", "rank"],
        foreignKeys = [ForeignKey(
                entity = LocalFood::class,
                parentColumns = ["code"],
                childColumns = ["foodCode"],
                onDelete = ForeignKey.CASCADE
            )])
data class LocalQuantity (
    val foodCode: String,
    val name: String,
    val rank: Int,
    val level: Int,
    val value: Double,
    val unit: String
)