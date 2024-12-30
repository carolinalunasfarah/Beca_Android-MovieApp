package com.example.beca_android_finalproject.presentation.features.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.beca_android_finalproject.ui.theme.Background

@Composable
fun LoadingIndicator() {
    Column (
        modifier = Modifier
            .background(Background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
        modifier = Modifier
            .padding(16.dp),
        color = MaterialTheme.colorScheme.primary
    )}

}

@Preview(showBackground = true)
@Composable
fun LoadingIndicatorPreview() {
    LoadingIndicator()
}