package com.simplefoodscanner.di

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Singleton

fun interface BarcodeScanner {
    fun scan() : Flow<String>
}

@Module
@InstallIn(SingletonComponent::class)
class BarcodeScanningModule {

    @Singleton
    @Provides
    fun provideBarcodeScanner(@ApplicationContext context: Context) = BarcodeScanner {
            val options = GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_EAN_13)
                .build()
            val scanner =  GmsBarcodeScanning.getClient(context, options)
            callbackFlow {
                scanner.startScan().addOnSuccessListener {
                    val barcode = it.displayValue
                    if (barcode == null) { cancel() } else { trySend(barcode) }
                }.addOnFailureListener { cancel() }.addOnCompleteListener { close() }
                awaitClose()
            }
        }
}