package com.simplefoodscanner.unit.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.simplefoodscanner.compose.FoodItem
import com.simplefoodscanner.data.Food
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class FoodItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayNameBrandsAndNutriscore() {
        // Arrange
        val food = Food(
            code = "1",
            name = "food_name",
            brands = "food_brands",
            date = LocalDateTime.now(),
            nutriscore = "A"
        )
        composeTestRule.setContent { FoodItem(food) }
        // Assert
        composeTestRule.onNodeWithText("food_name").assertIsDisplayed()
        composeTestRule.onNodeWithText("food_brands").assertIsDisplayed()
        composeTestRule.onNodeWithText("A").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayToday() {
        // Arrange
        val food = Food(
            code = "1",
            name = "food_name",
            brands = "food_brands",
            date = LocalDateTime.now(),
            nutriscore = "A"
        )
        composeTestRule.setContent { FoodItem(food) }
        // Assert
        composeTestRule.onNodeWithText("aujourd'hui").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayOneDayAgo() {
        // Arrange
        val food = Food(
            code = "1", name = "food_name", brands = "food_brands",
            date = LocalDateTime.now().minusDays(1),
            nutriscore = "A"
        )
        composeTestRule.setContent { FoodItem(food) }
        // Assert
        composeTestRule.onNodeWithText("il y a un jour").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayTwoDaysAgo() {
        // Arrange
        val food = Food(
            code = "1", name = "food_name", brands = "food_brands",
            date = LocalDateTime.now().minusDays(2),
            nutriscore = "A"
        )
        composeTestRule.setContent { FoodItem(food) }
        // Assert
        composeTestRule.onNodeWithText("il y a 2 jours").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayOneMonthAgo() {
        // Arrange
        val food = Food(code = "1",name = "food_name", brands = "food_brands",
            date = LocalDateTime.now().minusMonths(1),
            nutriscore = "A"
        )
        composeTestRule.setContent { FoodItem(food) }
        // Assert
        composeTestRule.onNodeWithText("il y a un mois").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayTwoMonthsAgo() {
        // Arrange
        val food = Food(code = "1",name = "food_name", brands = "food_brands",
            date = LocalDateTime.now().minusMonths(2),
            nutriscore = "A"
        )
        composeTestRule.setContent { FoodItem(food) }
        // Assert
        composeTestRule.onNodeWithText("il y a 2 mois").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayOneYearAgo() {
        // Arrange
        val food = Food(code = "1",name = "food_name", brands = "food_brands",
            date = LocalDateTime.now().minusYears(1),
            nutriscore = "A"
        )
        composeTestRule.setContent { FoodItem(food) }
        // Assert
        composeTestRule.onNodeWithText("il y a un an").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayTwoYearsAgo() {
        // Arrange
        val food = Food(code = "1",name = "food_name", brands = "food_brands",
            date = LocalDateTime.now().minusYears(2),
            nutriscore = "A"
        )
        composeTestRule.setContent { FoodItem(food) }
        // Assert
        composeTestRule.onNodeWithText("il y a 2 ans").assertIsDisplayed()
    }

    @Test
    fun shouldCallOnFoodClick() {
        // Arrange
        val food = Food(code = "1",name = "food_name", brands = "food_brands",
            date = LocalDateTime.now(),
            nutriscore = "A"
        )
        var called = false;
        composeTestRule.setContent { FoodItem(food, onFoodClick = { called = true }) }
        // Act
        composeTestRule.onRoot().performClick()
        // Assert
        assertTrue(called)
    }
}