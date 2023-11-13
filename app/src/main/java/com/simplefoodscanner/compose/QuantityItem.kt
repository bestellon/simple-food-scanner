package com.simplefoodscanner.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.simplefoodscanner.R
import com.simplefoodscanner.data.Quantity
import com.simplefoodscanner.ui.theme.FoodScannerTheme

@Composable
fun QuantityItem(quantity: Quantity) {
    val quantityIndent = dimensionResource(id = R.dimen.quantity_indent)*quantity.level
    ListItem(modifier = Modifier.testTag("quantityItem/${quantity.rank}").padding(start=quantityIndent),
        headlineContent = {
            Row {
                Text(quantity.name, modifier = Modifier.weight(1.0f))
                Text(quantity.value.toString()+" "+quantity.unit)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun QuantityItemPreview() {
    FoodScannerTheme() {
        QuantityItem(
            quantity = Quantity(foodCode = "1", name="name1", rank=1, level=3, value=12.3, unit="g")
        )
    }
}