package com.example.beca_android_finalproject.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.beca_android_finalproject.presentation.features.movies.MoviesScreen
import com.example.beca_android_finalproject.presentation.features.search.SearchScreen
import com.example.beca_android_finalproject.presentation.features.search.SearchViewModel
import com.example.beca_android_finalproject.presentation.viewmodel.MoviesViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val moviesViewModel = hiltViewModel<MoviesViewModel>()
    val searchViewModel = hiltViewModel<SearchViewModel>()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = { navController.navigate(ScreenNavigation.Movies.route) }) {
                    Icon(Icons.Filled.Home, contentDescription = "Movies")
                }
                IconButton(onClick = { navController.navigate(ScreenNavigation.Search.route) }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenNavigation.Movies.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ScreenNavigation.Movies.route) {
                MoviesScreen(
                    viewModel = moviesViewModel,
                    onMovieClick = { movieId ->
                        navController.navigate(ScreenNavigation.MovieDetails(movieId).createRoute(movieId))
                    }
                )
            }

            composable(ScreenNavigation.Search.route) {
                SearchScreen(
                    viewModel = searchViewModel,
                    onMovieClick = { movieId ->
                        navController.navigate(
                            ScreenNavigation.MovieDetails(movieId).createRoute(movieId)
                        )
                    }
                )
            }

            /*composable(
                route = ScreenNavigation.MovieDetails("{movieId}").route,
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                MovieDetailsScreen(navController, movieId)
            }*/
        }
    }
}
