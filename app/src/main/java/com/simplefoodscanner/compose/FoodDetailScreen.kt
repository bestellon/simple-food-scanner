package com.simplefoodscanner.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.simplefoodscanner.data.Food
import com.simplefoodscanner.data.Quantity
import com.simplefoodscanner.data.fakeFoods
import com.simplefoodscanner.data.fakeQuantitiesForFood0
import com.simplefoodscanner.ui.theme.FoodScannerTheme
import com.simplefoodscanner.viewmodels.FoodDetailViewModel

fun NavController.navigateToFoodDetail(foodCode: String) {
    navigate("foodDetail/${foodCode}")
}

fun NavGraphBuilder.foodDetail() {
    composable("foodDetail/{foodCode}",
        arguments = listOf(navArgument("foodCode") {type = NavType.StringType}))
    {
        FoodDetailScreen()
    }
}

internal class FoodDetailArgs(savedStateHandle: SavedStateHandle) {
    val foodCode = checkNotNull(savedStateHandle.get<String>("foodCode"))
}

@Composable
fun FoodDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: FoodDetailViewModel = hiltViewModel(),
) {
    val quantities by viewModel.quantities().collectAsState(initial = emptyList())
    val food by viewModel.food().collectAsState(initial = null)
    FoodDetail(modifier = modifier, food = food, quantities = quantities)
}

@Composable
fun FoodDetail(modifier: Modifier = Modifier,
               food: Food? = null,
               quantities: List<Quantity> = emptyList()) {
    Column(modifier = modifier.testTag("foodDetail")) {
        if (food != null) {
            FoodItem(food = food)
        }
        QuantityList(quantities = quantities)
    }
}

@Preview(showBackground = true)
@Composable
fun FoodDetailPreview() {
    FoodScannerTheme {
        FoodDetail(food = fakeFoods[0], quantities = fakeQuantitiesForFood0)
    }
}

@Composable
fun QuantityList(modifier: Modifier = Modifier,
                 quantities: List<Quantity> = emptyList()) {
    LazyColumn(modifier = modifier) {
        items(quantities) { quantity ->
            QuantityItem(quantity)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuantityListPreview() {
    FoodScannerTheme {
        QuantityList(quantities = fakeQuantitiesForFood0)
    }
}