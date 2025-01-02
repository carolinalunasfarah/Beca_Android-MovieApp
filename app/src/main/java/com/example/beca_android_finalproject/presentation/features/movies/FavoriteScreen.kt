package com.example.beca_android_finalproject.presentation.features.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beca_android_finalproject.presentation.features.components.MovieGrid
import com.example.beca_android_finalproject.presentation.features.composables.ErrorMessage
import com.example.beca_android_finalproject.presentation.features.composables.LoadingIndicator
import com.example.beca_android_finalproject.presentation.uimodel.uievents.MoviesUiEvent
import com.example.beca_android_finalproject.presentation.viewmodel.MoviesViewModel
import com.example.beca_android_finalproject.ui.theme.OnPrimary
import com.example.beca_android_finalproject.ui.theme.Secondary
import com.example.beca_android_finalproject.ui.theme.Surface

@Composable
fun FavoritesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit,
    onExploreMoviesClick: () -> Unit,
    isConnected: Boolean
) {
    val uiState by viewModel.uiState.collectAsState()
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Top
    ) {
        Text(
            text = "Favorite Movies",
            style = MaterialTheme.typography.titleLarge,
            color = OnPrimary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
        when {
            uiState.isLoading && favoriteMovies.isEmpty() -> {
                LoadingIndicator()
            }

            uiState.error != null && favoriteMovies.isEmpty() -> {
                ErrorMessage(
                    message = "Error getting favorite movies: ${uiState.error}"
                )
            }

            else -> {
                if (favoriteMovies.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        Text(
                            text = "You have no favorites movies (yet)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnPrimary
                        )
                        Spacer(modifier = Modifier.padding(8.dp))

                        Button(
                            onClick = onExploreMoviesClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Surface,
                                contentColor = Secondary
                            )
                        ) {
                            Text("Explore Movies")
                        }
                    }
                } else {
                    MovieGrid(
                        movies = favoriteMovies,
                        onMovieClick = onMovieClick,
                        onFavoriteClick = { movieId ->
                            viewModel.onEvent(MoviesUiEvent.ToggleFavorite(movieId))
                        },
                        onLoadMore = {},
                        isLoading = false,
                        errorMessage = "",
                        isConnected = isConnected,
                    )
                }
            }
        }
    }
}

