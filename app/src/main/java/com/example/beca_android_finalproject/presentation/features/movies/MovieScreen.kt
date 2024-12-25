package com.example.beca_android_finalproject.presentation.features.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beca_android_finalproject.domain.model.Movie
import com.example.beca_android_finalproject.presentation.features.details.composables.ErrorMessage
import com.example.beca_android_finalproject.presentation.features.details.composables.LoadingIndicator
import com.example.beca_android_finalproject.presentation.features.movies.composables.MovieCard
import com.example.beca_android_finalproject.presentation.uimodel.MoviesUiEvent
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
fun MovieGrid(
    movies: List<Movie>,
    isLoading: Boolean,
    errorMessage: String?,
    onMovieClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onLoadMore: () -> Unit
) {
    if (isLoading && movies.isEmpty()) {
        LoadingIndicator()
    } else if (errorMessage != null && movies.isEmpty()) {
        ErrorMessage(message = errorMessage)
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(movies) { movie ->
                MovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) },
                    onFavoriteClick = { onFavoriteClick(movie.id) }
                )
            }

            item {
                LoadMoreButton(onClick = onLoadMore)
            }
        }
    }
}


@Composable
fun LoadMoreButton(onClick: () -> Unit) {
}