package com.simplefoodscanner.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.simplefoodscanner.compose.FoodDetailArgs
import com.simplefoodscanner.data.FoodScannerService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle,
                                              private val foodScannerService: FoodScannerService
) : ViewModel() {
    private val args = FoodDetailArgs(savedStateHandle)

    fun quantities() = foodScannerService.quantities(args.foodCode)
    fun food() = foodScannerService.food(args.foodCode)
}