package com.simplefoodscanner.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [BarcodeScanningModule::class])
class FakeBarcodeScanningModule {
    @Singleton
    @Provides
    fun provideBarcodeScanner(@ApplicationContext context: Context, barcodeContainer: BarcodeContainer) = BarcodeScanner {
        flow {
            if (barcodeContainer.barcode == null) { throw Exception() }
            emit(barcodeContainer.barcode!!)
        }
    }
}