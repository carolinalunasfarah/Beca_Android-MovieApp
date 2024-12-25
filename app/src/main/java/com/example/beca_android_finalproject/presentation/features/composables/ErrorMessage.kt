package com.example.beca_android_finalproject.presentation.features.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorMessage(message: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Error: $message")
    }
}