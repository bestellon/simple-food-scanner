package com.simplefoodscanner.unit.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.simplefoodscanner.compose.QuantityItem
import com.simplefoodscanner.data.Quantity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuantityItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val quantity = Quantity(
        foodCode = "1",
        name = "quantity_name",
        rank = 1,
        level = 3,
        value = 2.3,
        unit = "kg"
    )


    @Test
    fun shouldDisplayAllQuantityData() {
        // Arrange
        composeTestRule.setContent { QuantityItem(quantity) }
        // Assert
        composeTestRule.onNodeWithText("quantity_name").assertIsDisplayed()
        composeTestRule.onNodeWithText("2.3 kg").assertIsDisplayed()
    }
}