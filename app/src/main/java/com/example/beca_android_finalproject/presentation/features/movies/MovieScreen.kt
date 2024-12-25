package com.example.beca_android_finalproject.presentation.features.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beca_android_finalproject.presentation.features.composables.ErrorMessage
import com.example.beca_android_finalproject.presentation.features.composables.LoadingIndicator
import com.example.beca_android_finalproject.presentation.features.components.MovieGrid
import com.example.beca_android_finalproject.presentation.uimodel.uievents.MoviesUiEvent
import com.example.beca_android_finalproject.presentation.viewmodel.MoviesViewModel


@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        when {
            uiState.isLoading && uiState.movies.isEmpty() -> {
                LoadingIndicator()
            }
            uiState.error != null && uiState.movies.isEmpty() -> {
                ErrorMessage(
                    message = "error"
                )
            }
            else -> {
                MovieGrid(
                    movies = uiState.movies,
                    onMovieClick = onMovieClick,
                    onFavoriteClick = { movieId ->
                        viewModel.onEvent(MoviesUiEvent.ToggleFavorite(movieId))
                    },
                    onLoadMore = {
                        viewModel.onEvent(MoviesUiEvent.LoadMore)
                    },
                    isLoading = false,
                    errorMessage = "",
                )
            }
        }
    }
}

@Composable
fun LoadMoreButton(onClick: () -> Unit) {
}