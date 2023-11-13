package com.simplefoodscanner.unit.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.simplefoodscanner.HiltTestActivity
import com.simplefoodscanner.compose.FoodScannerApp
import com.simplefoodscanner.data.fakeFoods
import com.simplefoodscanner.data.fakeQuantitiesForFood0
import com.simplefoodscanner.di.FoodScannerServiceModule
import com.simplefoodscanner.viewmodels.FoodDetailViewModel
import com.simplefoodscanner.viewmodels.FoodListViewModel
import com.simplefoodscanner.viewmodels.FoodScannerViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(FoodScannerServiceModule::class)
class FoodScannerAppTest {
    @get:Rule(order = 0)
    val mockkRule = MockKRule(this)

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @MockK(relaxed = true)
    @BindValue
    lateinit var foodListViewModel: FoodListViewModel

    @MockK(relaxed = true)
    @BindValue
    lateinit var foodDetailViewModel: FoodDetailViewModel

    @MockK(relaxed = true)
    @BindValue
    lateinit var foodScannerViewModel: FoodScannerViewModel

    @Before
    fun before() {
        // Arrange
        hiltRule.inject()
        every { foodListViewModel.foods() } returns flow {emit(fakeFoods) }
        every { foodDetailViewModel.food() } returns flow { emit(fakeFoods[1]) }
        every { foodDetailViewModel.quantities() } returns flow { emit(fakeQuantitiesForFood0) }
        composeTestRule.setContent { FoodScannerApp() }
    }

    @Test
    fun shouldDisplayFoodListAndAllFoodItemsWhenRouteIsFoodList() {
        // Assert
        composeTestRule.onNodeWithTag("foodList").assertIsDisplayed()
        for (food in fakeFoods) {
            composeTestRule.onNodeWithTag("foodItem/${food.code}").assertIsDisplayed()
        }
    }

    @Test
    fun shouldNavigateFromFoodListToFoodDetailWhenAFoodIsClicked() {
        // Act
        composeTestRule.onNodeWithTag("foodItem/1").performClick()
        // Assert
        composeTestRule.onNodeWithTag("foodDetail").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayFoodAndQuantitiesWhenRouteIsFoodDetail() {
        // Arrange
        composeTestRule.onNodeWithTag("foodItem/1").performClick()
        // Assert
        composeTestRule.onNodeWithTag("foodItem/1").assertIsDisplayed()
        for (quantity in fakeQuantitiesForFood0) {
            composeTestRule.onNodeWithTag("quantityItem/${quantity.rank}").assertIsDisplayed()
        }
    }

    @Test
    fun shouldNotDisplayAlertDialogAtStartUp() {
        // Assert
        composeTestRule.onNodeWithTag("alertDialog").assertDoesNotExist()
    }


    @Test
    fun shouldDisplayAlertDialogIfScanFoodFails() {
        // Arrange
        every { foodScannerViewModel.scanFoodAndDownload(any(), any()) } answers { secondArg<() -> Unit>().invoke() }
        // Act
        composeTestRule.onNodeWithTag("scanButton").performClick()
        // Assert
        composeTestRule.onNodeWithTag("alertDialog").assertIsDisplayed()
    }

    @Test
    fun shouldCloseAlertDialogWhenDismissButtonIsClicked() {
        // Arrange
        every { foodScannerViewModel.scanFoodAndDownload(any(), any()) } answers { secondArg<() -> Unit>().invoke() }
        composeTestRule.onNodeWithTag("scanButton").performClick()
        // Act
        composeTestRule.onNodeWithTag("dismissButton").performClick()
        // Assert
        composeTestRule.onNodeWithTag("alertDialog").assertDoesNotExist()
    }

    @Test
    fun shouldNavigateToFoodDetailIfScanFoodSucceeds() {
        // Arrange
        every { foodScannerViewModel.scanFoodAndDownload(any(), any()) } answers { firstArg<(String) -> Unit>().invoke("1"); }
        // Act
        composeTestRule.onNodeWithTag("scanButton").performClick()
        // Assert
        composeTestRule.onNodeWithTag("alertDialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("foodDetail").assertIsDisplayed()
        composeTestRule.onNodeWithTag("foodItem/1").assertIsDisplayed()
    }

    @Test
    fun shouldNavigateFromFoodDetailToFoodListWhenHomeIsClicked() {
        // Arrange
        composeTestRule.onNodeWithTag("foodItem/1").performClick()
        // Act
        composeTestRule.onNodeWithTag("home").performClick();
        // Assert
        composeTestRule.onNodeWithTag("foodList").assertIsDisplayed()
    }
}