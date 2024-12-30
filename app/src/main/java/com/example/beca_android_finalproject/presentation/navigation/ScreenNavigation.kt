package com.example.beca_android_finalproject.presentation.navigation

sealed class ScreenNavigation(val route: String) {

    data object Movies : ScreenNavigation("movies")

    data object Search : ScreenNavigation("search")

    data class MovieDetails(val movieId: Int) : ScreenNavigation("details/{movieId}") {
        constructor(movieId: String) : this(movieId.toIntOrNull() ?: 0)

        fun createRoute(movieId: Int) = "details/$movieId"
    }

    data object FavoritesMovies : ScreenNavigation("favorites")
}
