package com.example.beca_android_finalproject.presentation.navigation

sealed class ScreenNavigation(val route: String) {

    data object Movies : ScreenNavigation("movies")

    data object Search : ScreenNavigation("search")

    data class MovieDetails(val movieId: Int) : ScreenNavigation("details/{movieId}") {
        fun createRoute() = "details/${this.movieId}"
    }

    data object FavoritesMovies : ScreenNavigation("favorites")
}
