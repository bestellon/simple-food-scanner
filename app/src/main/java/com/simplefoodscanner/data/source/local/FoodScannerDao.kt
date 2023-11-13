package com.simplefoodscanner.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodScannerDao {
    @Query("SELECT * FROM foods ORDER BY date DESC")
    fun foods() : Flow<List<LocalFood>>

    @Query("SELECT * FROM quantities WHERE foodCode = :foodCode ORDER BY rank")
    fun quantities(foodCode : String) : Flow<List<LocalQuantity>>

    @Query("SELECT * FROM foods WHERE code = :foodCode")
    fun food(foodCode : String) : Flow<LocalFood?>

    @Upsert
    suspend fun insertFood(food: LocalFood, quantities: List<LocalQuantity>)
}