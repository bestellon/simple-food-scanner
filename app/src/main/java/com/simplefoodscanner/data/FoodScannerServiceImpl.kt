package com.simplefoodscanner.data

import android.util.Log
import com.simplefoodscanner.data.source.local.FoodScannerDao
import com.simplefoodscanner.data.source.local.LocalFood
import com.simplefoodscanner.data.source.local.LocalQuantity
import com.simplefoodscanner.data.source.network.FoodScannerNetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodScannerServiceImpl @Inject constructor(
    private val dao : FoodScannerDao, private val networkService : FoodScannerNetworkService
) :
    FoodScannerService {
    override fun foods(): Flow<List<Food>> = dao.foods().map(List<LocalFood>::toExternal)

    override fun food(foodCode: String): Flow<Food?> = dao.food(foodCode).map { food->food?.toExternal()}

    override fun quantities(foodCode: String): Flow<List<Quantity>> =
        dao.quantities(foodCode).map(List<LocalQuantity>::toExternal)

    override suspend fun downloadFood(foodCode: String): Boolean {
        return try {
            val networkFood = networkService.getFood(foodCode)
            val localFood = networkFood.toLocal()
            val localQuantities = networkFood.toLocalQuantities()
            dao.insertFood(localFood, localQuantities)
            true
        } catch(exception: Exception) {
            false
        }
    }
}