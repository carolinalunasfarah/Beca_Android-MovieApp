package com.example.beca_android_finalproject.presentation.features.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beca_android_finalproject.presentation.features.composables.ErrorMessage
import com.example.beca_android_finalproject.presentation.features.composables.LoadingIndicator
import com.example.beca_android_finalproject.presentation.features.components.MovieGrid
import com.example.beca_android_finalproject.presentation.features.composables.LoadMoreButton
import com.example.beca_android_finalproject.presentation.uimodel.uievents.MoviesUiEvent
import com.example.beca_android_finalproject.presentation.viewmodel.MoviesViewModel
import com.example.beca_android_finalproject.ui.theme.OnPrimary

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit,
    isConnected: Boolean
) {
    val uiState by viewModel.uiState.collectAsState()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(uiState.movies.size) {
        if (uiState.movies.isNotEmpty() && !uiState.isLoading) {
            lazyListState.scrollToItem(uiState.movies.size - 1)
        }
    }

    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Top,
        modifier = Modifier.fillMaxWidth()
        ) {
        Text(
            text = "Popular Movies",
            style = MaterialTheme.typography.titleLarge,
            color = OnPrimary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )

        when {
            uiState.isLoading && uiState.movies.isEmpty() -> {
                LoadingIndicator()
            }

            uiState.error != null && uiState.movies.isEmpty() -> {
                ErrorMessage(message = "Error loading popular movies: ${uiState.error}")
            }

            else -> {
                MovieGrid(
                    movies = uiState.movies,
                    isLoading = uiState.isLoading,
                    errorMessage = "",
                    onMovieClick = onMovieClick,
                    onFavoriteClick = { movieId -> viewModel.toggleFavorite(movieId) },
                    onLoadMore = {
                        viewModel.onEvent(MoviesUiEvent.LoadMore)
                    },
                    isConnected = isConnected
                )

                if (!uiState.isLoading && isConnected) {
                    LoadMoreButton(onClick = {
                        viewModel.onEvent(MoviesUiEvent.LoadMore)
                    })
                }
            }
        }
    }
}
