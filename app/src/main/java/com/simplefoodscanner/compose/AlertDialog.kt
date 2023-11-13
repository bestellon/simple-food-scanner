package com.simplefoodscanner.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.simplefoodscanner.R
import com.simplefoodscanner.ui.theme.FoodScannerTheme

@Composable
fun AlertDialog(errorMessage: String, onDismissRequest: () -> Unit) {
    androidx.compose.material3.AlertDialog(
        icon = {
            Icon(
                Icons.Default.Info
                , contentDescription = "DefaultIcon")
        },
        title = {
            Text(text = stringResource(R.string.error))
        },
        text = {
            Text(text = errorMessage)
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = { },
        dismissButton = {
            TextButton(
                modifier = Modifier.testTag("dismissButton"),
                onClick = { onDismissRequest() }
            ) {
                Text(stringResource(R.string.close))
            }
        },
        modifier = Modifier.testTag("alertDialog"))
}

@Preview(showBackground = true)
@Composable
fun AlertDialogPreview() {
    FoodScannerTheme() {
        AlertDialog("Preview message") { }
    }
}