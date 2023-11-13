package com.simplefoodscanner.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplefoodscanner.R
import com.simplefoodscanner.data.Food
import com.simplefoodscanner.ui.theme.FoodScannerTheme
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Composable
fun Nutriscore(nutriscore: String) {
    val nutriscoreSize = dimensionResource(id = R.dimen.nutriscore_size)
    val colorId = when(nutriscore) {
        "A" -> R.color.nutriscore_A
        "B" -> R.color.nutriscore_B
        "C" -> R.color.nutriscore_C
        "D" -> R.color.nutriscore_D
        "E" -> R.color.nutriscore_E
        else -> R.color.unknown_nutriscore
    }
    Text(nutriscore,
        style = MaterialTheme.typography.headlineSmall,
        color = Color.White,
        modifier = Modifier
            .size(nutriscoreSize)
            .background(color = colorResource(id = colorId), shape = CircleShape)
            .border(2.dp, Color.Black, CircleShape)
            .wrapContentHeight(align = Alignment.CenterVertically),
        textAlign = TextAlign.Center)
}

@Composable
private fun convertLocalDateTimeToString(date: LocalDateTime): String {
    val today = LocalDateTime.now()
    val dayCount = ChronoUnit.DAYS.between(date, today).toInt()
    val monthCount = ChronoUnit.MONTHS.between(date, today).toInt()
    val yearCount = ChronoUnit.YEARS.between(date, today).toInt()
    if (dayCount == 0) return stringResource(id = R.string.today)
    if (yearCount >= 1) return pluralStringResource(R.plurals.years_ago, yearCount, yearCount)
    if (monthCount >= 1) return pluralStringResource(R.plurals.months_ago, monthCount, monthCount)
    return pluralStringResource(R.plurals.days_ago, dayCount, dayCount)
}

@Composable
fun FoodItem(food: Food, modifier: Modifier = Modifier, onFoodClick: () -> Unit = {}) {
    ListItem(
        headlineContent = { Text(food.name) },
        supportingContent = { Text(food.brands) },
        trailingContent = { Text(convertLocalDateTimeToString(food.date)) },
        leadingContent = { Nutriscore(food.nutriscore) },
        modifier = modifier.fillMaxWidth().testTag("foodItem/${food.code}").clickable { onFoodClick() }
    )
}


@Preview(showBackground = true)
@Composable
fun FoodItemPreview() {
    FoodScannerTheme() {
        FoodItem(
            food = Food(code = "1",
                name = "food_name",
                brands = "food_brands",
                date = LocalDateTime.now(),
                nutriscore = "A")
        )
    }
}