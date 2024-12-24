package com.example.beca_android_finalproject.presentation.uimodel

sealed class MoviesUiEvent {
    data class ToggleFavorite(val movieId: Int) : MoviesUiEvent()
    data class SearchMovies(val query: String, val page: Int) : MoviesUiEvent()
    data class MovieDetails(val movieId: Int) : MoviesUiEvent()
    data object LoadMore : MoviesUiEvent()
}