package com.example.beca_android_finalproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.beca_android_finalproject.presentation.features.movies.MoviesScreen
import com.example.beca_android_finalproject.presentation.features.search.SearchScreen
import com.example.beca_android_finalproject.presentation.viewmodel.MoviesViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val moviesViewModel = hiltViewModel<MoviesViewModel>()

    NavHost(
        navController = navController,
        startDestination = ScreenNavigation.Movies.route
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
            SearchScreen()
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
