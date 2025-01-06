package com.example.beca_android_finalproject.presentation.features.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.beca_android_finalproject.presentation.features.components.MovieGrid
import com.example.beca_android_finalproject.presentation.features.composables.ErrorMessage
import com.example.beca_android_finalproject.presentation.features.composables.LoadingIndicator
import com.example.beca_android_finalproject.presentation.uimodel.uievents.MoviesUiEvent
import com.example.beca_android_finalproject.presentation.uimodel.uievents.SearchUiEvent
import com.example.beca_android_finalproject.presentation.viewmodel.MoviesViewModel
import com.example.beca_android_finalproject.ui.theme.OnPrimary

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    viewModelMovie: MoviesViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit,
    isConnected: Boolean
) {
    val uiState by viewModel.uiState.collectAsState()
    val favoriteMovies by viewModelMovie.favoriteMovies.collectAsState()
    var query by remember { mutableStateOf("") }

    val updatedMovies = uiState.movies.map { movie ->
        val isFavorite = favoriteMovies.any { it.id == movie.id }
        movie.copy(isFavorite = isFavorite)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Search Movies",
            style = MaterialTheme.typography.titleLarge,
            color = OnPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )

        TextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.onEvent(SearchUiEvent.SearchMovies(query, 1))
            },
            placeholder = { Text("Search movies...") },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        when {
            uiState.isLoading -> {
                LoadingIndicator()
            }

            uiState.error != null -> {
                ErrorMessage(
                    message = "Error searching movies: ${uiState.error}"
                )
            }

            uiState.movies.isEmpty() -> {
                Text(
                    text = "Type something to search",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = OnPrimary,
                )
            }

            else -> {
                MovieGrid(
                    movies = updatedMovies,
                    isLoading = false,
                    errorMessage = "",
                    onMovieClick = onMovieClick,
                    onFavoriteClick = { movieId ->
                        viewModelMovie.onEvent(MoviesUiEvent.ToggleFavorite(movieId))
                    },
                    isConnected = isConnected
                )
            }
        }
    }
}
