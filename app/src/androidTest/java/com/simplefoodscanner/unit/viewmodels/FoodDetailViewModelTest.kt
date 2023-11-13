package com.simplefoodscanner.unit.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.simplefoodscanner.data.FoodScannerService
import com.simplefoodscanner.data.fakeFoods
import com.simplefoodscanner.data.fakeQuantitiesForFood0
import com.simplefoodscanner.viewmodels.FoodDetailViewModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class FoodDetailViewModelTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var foodScannerService: FoodScannerService

    private lateinit var viewModel: FoodDetailViewModel

    private val expectedFood = fakeFoods[4]
    private val expectedFoodCode = expectedFood.code
    private val expectedQuantities = fakeQuantitiesForFood0

    @Before
    fun before() {
        // Arrange
        val savedStateHandle: SavedStateHandle = SavedStateHandle().apply {
            set("foodCode", expectedFoodCode)
        }
        every { foodScannerService.food(expectedFoodCode) } returns flow { emit(expectedFood) }
        every { foodScannerService.quantities(expectedFoodCode) } returns flow { emit(expectedQuantities) }
        viewModel = FoodDetailViewModel(savedStateHandle, foodScannerService)
    }

    @Test
    fun shouldReturnsFood() = runTest {
        // Act
        val food = viewModel.food().first()
        // Assert
        assertEquals(expectedFood, food)
    }

    @Test
    fun shouldReturnsQuantities() = runTest {
        // Act
        val quantities = viewModel.quantities().first()
        // Assert
        assertEquals(expectedQuantities, quantities)
    }
}