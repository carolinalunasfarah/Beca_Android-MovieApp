package com.example.beca_android_finalproject.presentation.features.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.beca_android_finalproject.R
import com.example.beca_android_finalproject.presentation.navigation.ScreenNavigation
import com.example.beca_android_finalproject.ui.theme.Primary
import com.example.beca_android_finalproject.ui.theme.Secondary
import com.example.beca_android_finalproject.ui.theme.Surface

@Composable
fun BottomNavBar(
    navController: NavHostController,
    isConnected: Boolean
) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

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

            if (isConnected) {
                IconButton(onClick = { navController.navigate(ScreenNavigation.Movies.route) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.movie),
                        contentDescription = "Movies",
                        tint = if (currentRoute == ScreenNavigation.Movies.route) Secondary else Primary,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }

                IconButton(onClick = { navController.navigate(ScreenNavigation.Search.route) }) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = if (currentRoute == ScreenNavigation.Search.route) Secondary else Primary,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }

                IconButton(onClick = { navController.navigate(ScreenNavigation.FavoritesMovies.route) }) {
                    Icon(
                        Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorites",
                        tint = if (currentRoute == ScreenNavigation.FavoritesMovies.route) Secondary else Primary,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            } else {
                Icon(
                    Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorites",
                    tint = Secondary,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    }
}


