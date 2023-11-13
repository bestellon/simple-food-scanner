package com.simplefoodscanner.di

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BarcodeContainer @Inject constructor() {
    var barcode: String? = null
}
