package com.simplefoodscanner.di

import com.simplefoodscanner.data.FoodScannerService
import com.simplefoodscanner.data.FoodScannerServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FoodScannerServiceModule {
    @Singleton
    @Binds
    abstract fun bindFoodScannerService(service: FoodScannerServiceImpl): FoodScannerService
}