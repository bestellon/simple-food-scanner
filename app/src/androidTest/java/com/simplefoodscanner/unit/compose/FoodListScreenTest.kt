package com.simplefoodscanner.unit.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.simplefoodscanner.compose.FoodListScreen
import com.simplefoodscanner.data.Food
import com.simplefoodscanner.data.fakeFoods
import com.simplefoodscanner.viewmodels.FoodListViewModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FoodListScreenTest {
    @get:Rule(order = 0)
    val mockkRule = MockKRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @MockK
    lateinit var mockViewModel: FoodListViewModel

    private val expectedFoods = fakeFoods

    @Before
    fun before() {
        // Arrange
        every { mockViewModel.foods() } returns flow {
            emit(expectedFoods)
        }
    }

    @Test
    fun shouldDisplayAllFoods() {
        // Arrange
        composeTestRule.setContent { FoodListScreen(viewModel = mockViewModel) }
        // Assert
        for (food in expectedFoods) {
            composeTestRule.onNodeWithTag("foodItem/${food.code}").assertIsDisplayed()
        }
    }

    @Test
    fun shouldCallOnFoodClickWhenFoodIsClicked() {
        // Arrange
        var foodClicked: Food? = null
        composeTestRule.setContent { FoodListScreen(viewModel = mockViewModel, onFoodClick = { food -> foodClicked = food }) }
        // Act
        composeTestRule.onNodeWithTag("foodItem/${expectedFoods[1].code}").performClick()
        // Assert
        assertEquals(expectedFoods[1], foodClicked)
    }
}