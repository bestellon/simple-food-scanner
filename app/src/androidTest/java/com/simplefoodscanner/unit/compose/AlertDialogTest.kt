package com.simplefoodscanner.unit.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.simplefoodscanner.compose.AlertDialog
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlertDialogTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayErrorMessage() {
        // Arrange
        composeTestRule.setContent { AlertDialog(errorMessage = "error_message", onDismissRequest = {}) }
        // Assert
        composeTestRule.onNodeWithText("error_message").assertIsDisplayed()
    }

    @Test
    fun shouldCallCallbackWhenDismissButtonIsClicked() {
        // Arrange
        var called = false
        composeTestRule.setContent { AlertDialog(errorMessage = "error_message", onDismissRequest = { called = true; }) }
        // Act
        composeTestRule.onNodeWithTag("dismissButton").performClick()
        // Assert
        assertTrue(called)
    }
}