package com.example.beca_android_finalproject.presentation.navigation

import WelcomeScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.beca_android_finalproject.presentation.features.components.BottomNavBar
import com.example.beca_android_finalproject.presentation.features.components.TopNavBar
import com.example.beca_android_finalproject.presentation.features.details.MovieDetailsScreen
import com.example.beca_android_finalproject.presentation.features.movies.FavoritesScreen
import com.example.beca_android_finalproject.presentation.features.movies.MoviesScreen
import com.example.beca_android_finalproject.presentation.features.search.SearchScreen
import com.example.beca_android_finalproject.presentation.features.search.SearchViewModel
import com.example.beca_android_finalproject.presentation.viewmodel.MoviesViewModel

import com.example.beca_android_finalproject.ui.theme.Background

@Composable
fun AppNavigation(navController: NavHostController) {
    val moviesViewModel = hiltViewModel<MoviesViewModel>()
    val searchViewModel = hiltViewModel<SearchViewModel>()
    val isConnected by moviesViewModel.isConnected.collectAsState()

    val currentRoute = navController.currentBackStackEntryFlow.collectAsState(initial = null)
        .value?.destination?.route

    val showBars = currentRoute != "welcome"

    Scaffold(
        topBar = {
            if (showBars) {
                TopNavBar()
            }
        },
        bottomBar = {
            if (showBars) {
                BottomNavBar(
                    navController = navController,
                    isConnected = isConnected
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "welcome",
            modifier = Modifier
                .padding(innerPadding)
                .background(Background)
        ) {
            composable("welcome") {
                WelcomeScreen(onNavigate = {
                    navController.navigate(ScreenNavigation.Movies.route) {
                        popUpTo("welcome") { inclusive = true }
                    }
                })
            }

            composable(ScreenNavigation.Movies.route) {
                if (isConnected) {
                    MoviesScreen(
                        viewModel = moviesViewModel,
                        onMovieClick = { movieId ->
                            navController.navigate(
                                ScreenNavigation.MovieDetails(movieId).createRoute()
                            )
                        },
                        isConnected = true
                    )
                } else {
                    FavoritesScreen(
                        viewModel = moviesViewModel,
                        onMovieClick = { movieId ->
                            navController.navigate(
                                ScreenNavigation.MovieDetails(movieId).createRoute()
                            )
                        },
                        onExploreMoviesClick = {
                            navController.navigate(ScreenNavigation.Movies.route)
                        },
                        isConnected = false
                    )
                }
            }

            composable(ScreenNavigation.Search.route) {
                SearchScreen(
                    viewModel = searchViewModel,
                    onMovieClick = { movieId ->
                        navController.navigate(
                            ScreenNavigation.MovieDetails(movieId).createRoute()
                        )
                    },
                    isConnected = isConnected
                )
            }

            composable(ScreenNavigation.FavoritesMovies.route) {
                FavoritesScreen(
                    viewModel = moviesViewModel,
                    onMovieClick = { movieId ->
                        navController.navigate(
                            ScreenNavigation.MovieDetails(movieId).createRoute()
                        )
                    },
                    onExploreMoviesClick = {
                        navController.navigate(ScreenNavigation.Movies.route)
                    },
                    isConnected = isConnected
                )
            }

            composable(
                route = "details/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                MovieDetailsScreen(
                    movieId = movieId,
                    viewModelFavorite = moviesViewModel
                )
            }

        }
    }
}

