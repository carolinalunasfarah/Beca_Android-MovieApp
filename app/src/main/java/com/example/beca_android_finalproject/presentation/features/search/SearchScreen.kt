package com.example.beca_android_finalproject.presentation.features.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    onMovieClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        TextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.onEvent(SearchUiEvent.SearchMovies(query, 1))
            },
            placeholder = { Text("Search movies...") },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        when {
            uiState.isLoading -> {
                LoadingIndicator()
            }

            uiState.error != null -> {
                ErrorMessage(
                    message = "error"
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
                    movies = uiState.movies,
                    onMovieClick = onMovieClick,
                    onFavoriteClick = { movieId ->
                        viewModelMovie.onEvent(MoviesUiEvent.ToggleFavorite(movieId))
                    },
                    onLoadMore = {
                        viewModelMovie.onEvent(MoviesUiEvent.LoadMore)
                    },
                    isLoading = false,
                    errorMessage = "",
                )
            }
        }
    }
}


