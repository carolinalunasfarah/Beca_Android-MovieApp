package com.example.beca_android_finalproject.presentation.features.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.beca_android_finalproject.presentation.navigation.ScreenNavigation
import com.example.beca_android_finalproject.ui.theme.OnSurface
import com.example.beca_android_finalproject.ui.theme.Secondary
import com.example.beca_android_finalproject.ui.theme.Surface

@Composable
fun BottomNavBar(navController: NavHostController) {
    BottomAppBar(
        containerColor = Surface,
        modifier = Modifier
            .height(50.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { navController.navigate(ScreenNavigation.Movies.route) }) {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Movies",
                    tint = Secondary,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
            IconButton(onClick = { navController.navigate(ScreenNavigation.Search.route) }) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Secondary,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
            IconButton(onClick = { navController.navigate(ScreenNavigation.FavoritesMovies.route) }) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorites",
                    tint = Secondary,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }

    }
}
