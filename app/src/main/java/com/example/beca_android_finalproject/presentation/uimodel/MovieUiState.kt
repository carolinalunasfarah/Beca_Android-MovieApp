package com.example.beca_android_finalproject.presentation.uimodel

import com.example.beca_android_finalproject.domain.model.Movie

data class MoviesUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)