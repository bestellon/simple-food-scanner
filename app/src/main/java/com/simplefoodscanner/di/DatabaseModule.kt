package com.simplefoodscanner.di

import android.content.Context
import androidx.room.Room
import com.simplefoodscanner.data.source.local.FoodScannerDao
import com.simplefoodscanner.data.source.local.FoodScannerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideFoodScannerDatabase(@ApplicationContext context: Context): FoodScannerDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FoodScannerDatabase::class.java,
            "FoodScanner.db"
        ).build()
    }

    @Provides
    fun provideFoodScannerDao(database: FoodScannerDatabase): FoodScannerDao = database.dao()
}