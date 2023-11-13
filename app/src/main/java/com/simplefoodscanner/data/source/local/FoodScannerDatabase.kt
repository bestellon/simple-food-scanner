package com.simplefoodscanner.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LocalFood::class, LocalQuantity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class FoodScannerDatabase : RoomDatabase() {
    abstract fun dao() : FoodScannerDao
}