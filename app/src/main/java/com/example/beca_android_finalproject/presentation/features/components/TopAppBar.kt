package com.example.beca_android_finalproject.presentation.features.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.beca_android_finalproject.ui.theme.Secondary
import com.example.beca_android_finalproject.ui.theme.Surface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(
    containerColor: Color = Surface,
    contentColor: Color = Secondary,
    modifier: Modifier = Modifier
        .height(50.dp)
) {
    TopAppBar(
        title = { TopNavBarTitle("Movies App", contentColor) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = contentColor
        ),
        modifier = modifier
    )
}

@Composable
fun TopNavBarTitle(s: String, contentColor: Color) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = s,
            style = MaterialTheme.typography.titleLarge,
            color = contentColor,
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}
