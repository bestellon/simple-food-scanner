package com.simplefoodscanner.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class Url

@Module
@InstallIn(SingletonComponent::class)
class UrlModule {
    @Provides
    @Url
    @Singleton
    fun provideUrl(): String = "http://10.0.2.2:8000/index.php/"

}