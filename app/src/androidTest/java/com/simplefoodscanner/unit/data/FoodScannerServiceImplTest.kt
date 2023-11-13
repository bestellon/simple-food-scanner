package com.simplefoodscanner.unit.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.simplefoodscanner.data.Food
import com.simplefoodscanner.data.FoodScannerService
import com.simplefoodscanner.data.Quantity
import com.simplefoodscanner.data.fakeFoods
import com.simplefoodscanner.data.fakeQuantitiesForFood0
import com.simplefoodscanner.data.fakeQuantitiesForFood1
import com.simplefoodscanner.data.source.local.FoodScannerDao
import com.simplefoodscanner.data.toLocal
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FoodScannerServiceImplTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var dao: FoodScannerDao

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
    fun shouldReturnFoodFromCode() = runTest {
        // Arrange
        val expectedFood = fakeFoods[0]
        dao.insertFood(expectedFood.toLocal(), emptyList())
        // Act
        val food = service.food(expectedFood.code).first()
        // Assert
        assertEquals(expectedFood, food)
    }

    @Test
    fun shouldReturnNullIfFoodDoesNotExist() = runTest {
        // Arrange
        val food = fakeFoods[0]
        dao.insertFood(food.toLocal(), emptyList())
        // Act
        val result = service.food("123").first()
        // Assert
        assertEquals(null, result)
    }

    @Test
    fun shouldReturnAllFoodsOrderedByDateDesc() = runTest {
        // Arrange
        for (food in fakeFoods.reversed()) {
            dao.insertFood(food.toLocal(), emptyList())
        }
        // Act
        val result = service.foods().first()
        // Assert
        assertEquals(fakeFoods, result)
    }

    @Test
    fun shouldReturnFoodQuantitiesOrderedByRank() = runTest {
        // Arrange
        val expectedFood1 = fakeFoods[0]
        val expectedFood2 = fakeFoods[1]
        dao.insertFood(expectedFood1.toLocal(), fakeQuantitiesForFood0.reversed().toLocal())
        dao.insertFood(expectedFood2.toLocal(), fakeQuantitiesForFood1.reversed().toLocal())
        // Act
        val result = service.quantities(expectedFood1.code).first()
        // Assert
        assertEquals(fakeQuantitiesForFood0, result)
    }

    @Test
    fun shouldFailWhenFoodCodeQuantitiesDoesNotExists() = runTest {
        // Arrange
        val expectedFood1 = fakeFoods[0]
        // Act
        val result = runCatching {
            dao.insertFood(expectedFood1.toLocal(), fakeQuantitiesForFood1.reversed().toLocal())
        }
        // Assert
        assertTrue(result.isFailure)
    }

    @Test
    fun shouldDownloadFoodFromCode() = runTest {
        // Arrange
        val mockResponse: MockResponse = MockResponse()
            .addHeader("Content-Type", "application/json")
            .setResponseCode(200)
            .setBody("""
              {"code":"123","name":"A","brands":"B","nutriscore":"C",
               "quantities":[
                 {"name":"Q1","rank":0,"level":0,"quantity":14,"unit":"kcal"},
                 {"name":"Q2","rank":1,"level":2,"quantity":1.5,"unit":"g"}
               ]
              }
            """)
        mockWebServer.enqueue(mockResponse)
        val expectedFood = Food("123","A", "B", "C", LocalDateTime.now())
        val expectedQuantities = listOf(
            Quantity("123", "Q1", 0,0,14.0, "kcal"),
            Quantity("123", "Q2", 1,2,1.5, "g")
        )
        // Act
        val result = service.downloadFood("123")
        val food = service.food("123").first()!!
        val quantities = service.quantities("123").first()
        // Assert
        assertEquals(1, mockWebServer.requestCount)
        assertEquals("/food/123", mockWebServer.takeRequest().path)
        assertTrue(result)
        assertEquals(expectedFood.name, food.name)
        assertEquals(expectedFood.brands, food.brands)
        assertEquals(expectedFood.nutriscore, food.nutriscore)
        assertEquals(expectedFood.date.toLocalDate(), food.date.toLocalDate())
        assertEquals(expectedQuantities, quantities)
    }

    @Test
    fun downloadFoodShouldReturnFalseWhenWebServerReturnsIncompleteData() = runTest {
        // Arrange
        val mockResponse: MockResponse = MockResponse()
            .addHeader("Content-Type", "application/json")
            .setResponseCode(200)
            .setBody("""
              {"code":"123","name":"A","brands":"B","nutriscore":"C",
               "quantities":[
                 {"name":"Q1","rank":0,"level":0,"unit":"kcal"},
                 {"name":"Q2","rank":1,"level":2,"quantity":1.5,"unit":"g"}
               ]
              }
            """)
        mockWebServer.enqueue(mockResponse)
        // Act
        val result = service.downloadFood("123")
        // Assert
        assertEquals(mockWebServer.requestCount, 1)
        assertEquals(mockWebServer.takeRequest().path, "/food/123")
        assertFalse(result)
    }

    @Test
    fun downloadFoodShouldReturnFalseWhenWebServerIsShutDown() = runTest {
        // Arrange
        mockWebServer.shutdown()
        // Act
        val result = service.downloadFood("123")
        // Assert
        assertFalse(result)
    }
}