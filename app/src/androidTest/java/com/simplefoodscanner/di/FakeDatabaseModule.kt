package com.simplefoodscanner.di

import android.content.Context
import androidx.room.Room
import com.simplefoodscanner.data.source.local.FoodScannerDao
import com.simplefoodscanner.data.source.local.FoodScannerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DatabaseModule::class])
class FakeDatabaseModule {
    @Singleton
    @Provides
    fun provideFoodScannerDatabase(@ApplicationContext context: Context): FoodScannerDatabase {
        return Room.inMemoryDatabaseBuilder(
            context.applicationContext,
            FoodScannerDatabase::class.java
        ).build()
    }

    @Provides
    fun provideFoodScannerDao(database: FoodScannerDatabase): FoodScannerDao = database.dao()
}