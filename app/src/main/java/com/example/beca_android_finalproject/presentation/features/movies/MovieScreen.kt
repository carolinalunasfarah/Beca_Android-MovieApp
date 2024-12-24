package com.example.beca_android_finalproject.presentation.features.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun MoviesScreen() {
    Column {
        Text(
            text = "¡Hola Compose!",
            style = MaterialTheme.typography.headlineMedium
        )
    }

}
// Preview
@Preview(showBackground = true)
@Composable
fun HelloComposePreview() {
    MaterialTheme {
        MoviesScreen()
    }
}