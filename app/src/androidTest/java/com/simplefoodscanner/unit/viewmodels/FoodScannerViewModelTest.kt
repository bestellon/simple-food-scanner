package com.simplefoodscanner.unit.viewmodels

import com.simplefoodscanner.data.FoodScannerService
import com.simplefoodscanner.di.BarcodeContainer
import com.simplefoodscanner.di.BarcodeScanner
import com.simplefoodscanner.viewmodels.FoodScannerViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@HiltAndroidTest
@RunWith(JUnit4::class)
class FoodScannerViewModelTest {
    @get:Rule(order = 0)
    val mockkRule = MockKRule(this)

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @MockK
    lateinit var foodScannerService: FoodScannerService

    @Inject
    lateinit var barcodeContainer : BarcodeContainer

    @Inject
    lateinit var barcodeScanner : BarcodeScanner

    private lateinit var viewModel : FoodScannerViewModel


    private var success = false
    private var failure = false

    @Before
    fun before() {
        // Arrange
        hiltRule.inject()
        viewModel = FoodScannerViewModel(barcodeScanner, foodScannerService)
        success = false
        failure = false
    }

    @Test
    fun shouldSucceedWhenBarcodeScannerAndDownloadFoodSucceed() = runBlocking {
        // Arrange
        barcodeContainer.barcode = "1234575124"
        coEvery { foodScannerService.downloadFood(barcodeContainer.barcode!!) } returns true
        // Act
        scan()
        // Assert
        assertSuccess()
    }

    @Test
    fun shouldFailWhenBarcodeScannerFails() = runBlocking {
        // Act
        scan()
        // Assert
        assertFailure()
    }

    @Test
    fun shouldFailWhenDownloadFoodFails() = runBlocking {
        // Arrange
        barcodeContainer.barcode = "1234575124"
        coEvery { foodScannerService.downloadFood(barcodeContainer.barcode!!) } returns false
        // Act
        scan()
        // Assert
        assertFailure()
    }

    private fun scan() {
        viewModel.scanFoodAndDownload({success = true }, { failure = true })
    }

    private suspend fun assertSuccess() {
        delay(10)
        assertTrue(success)
        assertFalse(failure)
    }

    private suspend fun assertFailure() {
        delay(10)
        assertFalse(success)
        assertTrue(failure)
    }
}