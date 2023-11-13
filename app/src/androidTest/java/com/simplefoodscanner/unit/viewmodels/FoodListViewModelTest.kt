package com.simplefoodscanner.unit.viewmodels

import com.simplefoodscanner.data.FoodScannerService
import com.simplefoodscanner.data.fakeFoods
import com.simplefoodscanner.viewmodels.FoodListViewModel
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
class FoodListViewModelTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var foodScannerService: FoodScannerService

    private lateinit var viewModel: FoodListViewModel

    private val expectedFoods = fakeFoods

    @Before
    fun before() {
        // Arrange
        every { foodScannerService.foods() } returns flow { emit(expectedFoods) }
        viewModel = FoodListViewModel(foodScannerService)
    }

    @Test
    fun foods() = runTest {
        // Act
        val foods = viewModel.foods().first()
        // Assert
        assertEquals(expectedFoods, foods)
    }
}