package com.simplefoodscanner.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.simplefoodscanner.MainActivity
import com.simplefoodscanner.data.fakeFoods
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
class FoodListFeatureTest {
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
            for (food in fakeFoods) {
                dao.insertFood(food.toLocal(), emptyList())
            }
        }
    }

    @Test
    fun shouldDisplayAllFoods() {
        // Assert
        for (food in fakeFoods) {
            composeTestRule.onNodeWithTag("foodItem/${food.code}").assertIsDisplayed()
        }
    }
}