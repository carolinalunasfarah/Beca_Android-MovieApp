package com.example.beca_android_finalproject.presentation.features.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.beca_android_finalproject.presentation.navigation.ScreenNavigation

@Composable
fun BottomNavBar(navController: NavHostController) {
    BottomAppBar {
        IconButton(onClick = { navController.navigate(ScreenNavigation.Movies.route) }) {
            Icon(Icons.Filled.Home, contentDescription = "Movies")
        }
        IconButton(onClick = { navController.navigate(ScreenNavigation.Search.route) }) {
            Icon(Icons.Filled.Search, contentDescription = "Search")
        }
        IconButton(onClick = { navController.navigate(ScreenNavigation.FavoritesMovies.route) }) {
            Icon(Icons.Filled.Favorite, contentDescription = "Favorites")
        }
    }
}
