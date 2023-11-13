package com.simplefoodscanner.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.simplefoodscanner.MainActivity
import com.simplefoodscanner.data.fakeFoods
import com.simplefoodscanner.data.fakeQuantitiesForFood0
import com.simplefoodscanner.data.fakeQuantitiesForFood1
import com.simplefoodscanner.data.source.local.FoodScannerDao
import com.simplefoodscanner.data.toLocal
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FoodDetailFeatureTest {
    @get:Rule(order = 0)
    val mockkRule = MockKRule(this)

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var dao: FoodScannerDao

    @Before
    fun before() {
        // Arrange
        hiltRule.inject()
        runTest {
            dao.insertFood(fakeFoods[0].toLocal(), fakeQuantitiesForFood0.toLocal())
            dao.insertFood(fakeFoods[1].toLocal(), fakeQuantitiesForFood1.toLocal())
        }
    }

    @Test
    fun shouldNavigateFromFoodListToFoodDetailWhenAFoodIsClicked() {
        // Act
        navigateToFoodDetail1()
        // Assert
        composeTestRule.onNodeWithTag("foodDetail").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayFoodItem() {
        // Arrange
        navigateToFoodDetail1()
        // Assert
        composeTestRule.onNodeWithTag("foodItem/1").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayAllQuantities() {
        // Arrange
        navigateToFoodDetail1()
        // Assert
        for (quantity in fakeQuantitiesForFood1) {
            composeTestRule.onNodeWithTag("quantityItem/${quantity.rank}").assertIsDisplayed()
        }
    }

    @Test
    fun shouldNavigateFromFoodDetailToFoodListWhenHomeIsClicked() {
        // Arrange
        navigateToFoodDetail1()
        // Act
        composeTestRule.onNodeWithTag("home").performClick();
        // Assert
        composeTestRule.onNodeWithTag("foodList").assertIsDisplayed()
    }

    private fun navigateToFoodDetail1() {
        composeTestRule.onNodeWithTag("foodItem/1").performClick()
    }
}