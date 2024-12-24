package com.example.beca_android_finalproject.presentation.features.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beca_android_finalproject.domain.model.Movie
import com.example.beca_android_finalproject.presentation.uimodel.MoviesUiEvent
import com.example.beca_android_finalproject.presentation.viewmodel.MoviesViewModel


@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        Text("Loading: ${uiState.isLoading}")
        Text("Movies size: ${uiState.movies.size}")

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
                    }
                )
            }
        }
    }
}

@Composable
fun MovieGrid(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onLoadMore: () -> Unit
) {
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

@Composable
fun LoadMoreButton(onClick: () -> Unit) {

}

@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column {
            Text(movie.title)
            IconButton(onClick = onFavoriteClick) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite")
            }
        }
    }
}

@Composable
fun ErrorMessage(message: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Error: $message")
    }
}

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.padding(16.dp),
        color = MaterialTheme.colorScheme.primary
    )
}