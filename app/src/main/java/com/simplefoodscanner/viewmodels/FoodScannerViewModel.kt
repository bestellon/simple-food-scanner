package com.simplefoodscanner.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplefoodscanner.data.FoodScannerService
import com.simplefoodscanner.di.BarcodeScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FoodScannerViewModel @Inject constructor(
    private val barcodeScanner: BarcodeScanner,
    private val foodScannerService: FoodScannerService
): ViewModel() {

    fun scanFoodAndDownload(onSuccess: (String) -> Unit, onFailure : () -> Unit) {
        viewModelScope.launch {
            barcodeScanner.scan().catch { onFailure() }.collect { barcode ->
                val result = foodScannerService.downloadFood(barcode)
                if (result) {
                    onSuccess(barcode)
                } else {
                    onFailure()
                }
            }
        }
    }
}

