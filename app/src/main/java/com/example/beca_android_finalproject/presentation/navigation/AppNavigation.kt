package com.example.beca_android_finalproject.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.beca_android_finalproject.presentation.features.components.BottomNavBar
import com.example.beca_android_finalproject.presentation.features.details.MovieDetailsScreen
import com.example.beca_android_finalproject.presentation.features.details.MovieDetailsViewModel
import com.example.beca_android_finalproject.presentation.features.movies.MoviesScreen
import com.example.beca_android_finalproject.presentation.features.search.SearchScreen
import com.example.beca_android_finalproject.presentation.features.search.SearchViewModel
import com.example.beca_android_finalproject.presentation.features.movies.FavoritesScreen
import com.example.beca_android_finalproject.presentation.viewmodel.MoviesViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val moviesViewModel = hiltViewModel<MoviesViewModel>()
    val searchViewModel = hiltViewModel<SearchViewModel>()
    //val detailsViewModel = hiltViewModel<MovieDetailsViewModel>()

    Scaffold(bottomBar = {
        BottomNavBar(navController = navController)
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenNavigation.Movies.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ScreenNavigation.Movies.route) {
                MoviesScreen(viewModel = moviesViewModel, onMovieClick = { movieId ->
                    navController.navigate(
                        ScreenNavigation.MovieDetails(movieId).createRoute(movieId)
                    )
                })
            }

            composable(ScreenNavigation.Search.route) {
                SearchScreen(viewModel = searchViewModel, onMovieClick = { movieId ->
                    navController.navigate(
                        ScreenNavigation.MovieDetails(movieId).createRoute(movieId)
                    )
                })
            }

            composable(ScreenNavigation.FavoritesMovies.route) {
                FavoritesScreen(viewModel = moviesViewModel, onMovieClick = { movieId ->
                    navController.navigate(ScreenNavigation.MovieDetails(movieId).createRoute(movieId))
                })
            }

            /*composable(
                route = ScreenNavigation.MovieDetails("{movieId}").route,
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                MovieDetailsScreen(
                    movieId = movieId,
                    viewModel = detailsViewModel,
                    viewModelFavorite = moviesViewModel
                )
            }*/
        }
    }
}

