package com.simplefoodscanner.viewmodels

import androidx.lifecycle.ViewModel
import com.simplefoodscanner.data.FoodScannerService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(private val foodScannerService: FoodScannerService) : ViewModel() {
    fun foods() = foodScannerService.foods()
}