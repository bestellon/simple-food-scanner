package com.simplefoodscanner.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.simplefoodscanner.data.source.network.FoodScannerNetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideFoodScannerNetworkService(@Url url: String): FoodScannerNetworkService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(url)
            .build()
        return retrofit.create(FoodScannerNetworkService::class.java)
    }
}