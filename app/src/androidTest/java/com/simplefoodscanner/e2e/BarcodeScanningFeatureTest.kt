package com.simplefoodscanner.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.simplefoodscanner.MainActivity
import com.simplefoodscanner.data.Food
import com.simplefoodscanner.data.FoodScannerService
import com.simplefoodscanner.data.Quantity
import com.simplefoodscanner.data.source.local.FoodScannerDao
import com.simplefoodscanner.data.toExternal
import com.simplefoodscanner.di.BarcodeContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class BarcodeScanningFeatureTest {
    @get:Rule(order = 0)
    val mockkRule = MockKRule(this)

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var dao: FoodScannerDao

    @Inject
    lateinit var barcodeContainer : BarcodeContainer

    @Inject
    lateinit var service : FoodScannerService

    private val mockWebServer = MockWebServer()

    @Before
    fun before() {
        // Arrange
        mockWebServer.start(8080)
        hiltRule.inject()
    }

    @After
    @Throws(IOException::class)
    fun after() {
        mockWebServer.shutdown()
    }

    @Test
    fun shouldScanAndDownloadFood(): Unit = runBlocking {
        // Arrange
        barcodeContainer.barcode = "123"
        addHttpResponse()
        val expectedFood = Food("123", "A", "B", "C", LocalDateTime.now())
        val expectedQuantities = listOf(
            Quantity("123", "Q1", 0, 0, 14.0, "kcal"),
            Quantity("123", "Q2", 1, 2, 1.5, "g")
        )
        // Act
        composeTestRule.onNodeWithTag("scanButton").performClick()
        delay(100)
        // Assert
        val food = dao.food("123").first()!!.toExternal()
        val quantities = dao.quantities("123").first().toExternal()
        Assert.assertEquals(1, mockWebServer.requestCount)
        Assert.assertEquals("/food/123", mockWebServer.takeRequest().path,)
        Assert.assertEquals(expectedFood.name, food.name)
        Assert.assertEquals(expectedFood.brands, food.brands)
        Assert.assertEquals(expectedFood.nutriscore, food.nutriscore)
        Assert.assertEquals(expectedFood.date.toLocalDate(), food.date.toLocalDate())
        Assert.assertEquals(expectedQuantities, quantities)
        composeTestRule.onNodeWithTag("foodDetail").assertIsDisplayed()
        composeTestRule.onNodeWithTag("foodItem/123").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayAlertDialogWhenBarcodeScanningFails(): Unit = runBlocking {
        // Arrange
        barcodeContainer.barcode = null
        addHttpResponse()
        // Act
        composeTestRule.onNodeWithTag("scanButton").performClick()
        delay(100)
        // Assert
        composeTestRule.onNodeWithTag("alertDialog").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayAlertDialogWhenServerIsShutDown(): Unit = runBlocking {
        // Arrange
        mockWebServer.shutdown()
        barcodeContainer.barcode = "123"
        addHttpResponse()
        // Act
        composeTestRule.onNodeWithTag("scanButton").performClick()
        delay(100)
        // Assert
        composeTestRule.onNodeWithTag("alertDialog").assertIsDisplayed()
    }

    @Test
    fun shouldCloseAlertDialogWhenDismissButtonIsClicked() {
        // Arrange
        barcodeContainer.barcode = null
        composeTestRule.onNodeWithTag("scanButton").performClick()
        // Act
        composeTestRule.onNodeWithTag("dismissButton").performClick()
        // Assert
        composeTestRule.onNodeWithTag("alertDialog").assertDoesNotExist()
    }

    private fun addHttpResponse() {
        val mockResponse: MockResponse = MockResponse()
            .addHeader("Content-Type", "application/json")
            .setResponseCode(200)
            .setBody(
                """
              {"code":"123","name":"A","brands":"B","nutriscore":"C",
               "quantities":[
                 {"name":"Q1","rank":0,"level":0,"quantity":14,"unit":"kcal"},
                 {"name":"Q2","rank":1,"level":2,"quantity":1.5,"unit":"g"}
               ]
              }
            """
            )
        mockWebServer.enqueue(mockResponse)
    }

}