package com.simplefoodscanner.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.simplefoodscanner.R
import com.simplefoodscanner.viewmodels.FoodScannerViewModel


@Composable
fun FoodScannerApp(navController: NavHostController = rememberNavController(),
                   viewModel : FoodScannerViewModel = hiltViewModel()) {

    var errorMessage : String? by remember { mutableStateOf(null) }
    if (errorMessage != null) {
        AlertDialog(errorMessage!!) { errorMessage = null }
    }

    Scaffold(
        topBar = {
            FoodScannerTopBar(onHomeClick = navController::navigateToFoodList)
        },
        floatingActionButton = {
            val scanFoodError = stringResource(R.string.scanFoodError)
            FoodScannerFloatingActionButton(
                onClick = {
                    viewModel.scanFoodAndDownload(onSuccess = navController::navigateToFoodDetail,
                                               onFailure = { errorMessage = scanFoodError } )}
            )
        }
    ) {
        FoodScannerNavHost(
            navController = navController,
            modifier = Modifier.padding(it)
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodScannerTopBar(onHomeClick : () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text("Food Scanner") },
        navigationIcon = {
            IconButton(onClick = onHomeClick, modifier = Modifier.testTag("home")) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
            }
        },
    )
}

@Composable
fun FoodScannerFloatingActionButton(onClick : () -> Unit) {
    val scanFoodError = stringResource(R.string.scanFoodError)
    FloatingActionButton(
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
        modifier = Modifier.testTag("scanButton")
    ) {
        Icon(Icons.Filled.Add, "Scan")
    }
}

@Composable
fun FoodScannerNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "foodList", modifier = modifier) {
        foodList(navController)
        foodDetail()
    }
}
