package com.simplefoodscanner.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [UrlModule::class])
class FakeUrlModule {
    @Provides
    @Url
    @Singleton
    fun provideUrl() : String = "http://127.0.0.1:8080"
}