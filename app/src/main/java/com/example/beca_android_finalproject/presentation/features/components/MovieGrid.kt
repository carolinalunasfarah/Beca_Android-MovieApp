package com.example.beca_android_finalproject.presentation.features.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.beca_android_finalproject.domain.model.Movie
import com.example.beca_android_finalproject.presentation.features.composables.ErrorMessage
import com.example.beca_android_finalproject.presentation.features.composables.LoadingIndicator
import com.example.beca_android_finalproject.presentation.features.movies.LoadMoreButton

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
