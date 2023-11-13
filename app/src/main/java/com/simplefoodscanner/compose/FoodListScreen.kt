package com.simplefoodscanner.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.simplefoodscanner.data.Food
import com.simplefoodscanner.data.fakeFoods
import com.simplefoodscanner.ui.theme.FoodScannerTheme
import com.simplefoodscanner.viewmodels.FoodListViewModel

fun NavController.navigateToFoodList() {
    navigate("foodList")
}

fun NavGraphBuilder.foodList(navController: NavController) {
    composable("foodList") {
        FoodListScreen(onFoodClick = {navController.navigateToFoodDetail(it.code) })
    }
}

@Composable
fun FoodListScreen(
    modifier: Modifier = Modifier,
    viewModel: FoodListViewModel = hiltViewModel(),
    onFoodClick: (Food) -> Unit = {},
) {
    val foods by viewModel.foods().collectAsState(initial = emptyList())
    FoodList(modifier = modifier, foods = foods, onFoodClick = onFoodClick)
}

@Composable
fun FoodList(modifier: Modifier = Modifier,
             foods: List<Food> = emptyList(),
             onFoodClick: (Food) -> Unit = {}) {
    LazyColumn(modifier = modifier.testTag("foodList")) {
        items(foods) {food ->
            FoodItem(food, onFoodClick = { onFoodClick(food) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodListPreview() {
    FoodScannerTheme {
        FoodList(foods = fakeFoods)
    }
}

