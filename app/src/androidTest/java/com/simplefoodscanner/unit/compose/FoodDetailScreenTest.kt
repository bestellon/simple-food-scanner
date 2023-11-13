package com.simplefoodscanner.unit.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.simplefoodscanner.compose.FoodDetailScreen
import com.simplefoodscanner.data.fakeFoods
import com.simplefoodscanner.data.fakeQuantitiesForFood0
import com.simplefoodscanner.viewmodels.FoodDetailViewModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FoodDetailScreenTest {
    @get:Rule(order = 0)
    val mockkRule = MockKRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @MockK
    lateinit var mockViewModel: FoodDetailViewModel

    private val expectedFood = fakeFoods[0]
    private val expectedQuantities = fakeQuantitiesForFood0

    @Before
    fun before() {
        // Arrange
        every { mockViewModel.food() } returns flow {
            emit(expectedFood)
        }
        every { mockViewModel.quantities() } returns flow {
            emit(expectedQuantities)
        }
        composeTestRule.setContent { FoodDetailScreen(viewModel = mockViewModel) }
    }

    @Test
    fun shouldDisplayFoodItem() {
        // Assert
        composeTestRule.onNodeWithTag("foodItem/${expectedFood.code}").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayAllQuantities() {
        // Assert
        for (quantity in expectedQuantities) {
            composeTestRule.onNodeWithTag("quantityItem/${quantity.rank}").assertIsDisplayed()
        }
    }

}