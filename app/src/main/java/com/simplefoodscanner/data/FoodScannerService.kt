package com.simplefoodscanner.data

import kotlinx.coroutines.flow.Flow

interface FoodScannerService {
    fun foods(): Flow<List<Food>>
    fun food(foodCode: String): Flow<Food?>
    fun quantities(foodCode: String): Flow<List<Quantity>>

    suspend fun downloadFood(foodCode: String): Boolean
}