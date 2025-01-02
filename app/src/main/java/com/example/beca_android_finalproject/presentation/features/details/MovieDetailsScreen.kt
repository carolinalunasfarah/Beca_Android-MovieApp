package com.example.beca_android_finalproject.presentation.features.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.beca_android_finalproject.domain.model.Movie
import com.example.beca_android_finalproject.presentation.features.composables.ErrorMessage
import com.example.beca_android_finalproject.presentation.features.composables.LoadingIndicator
import com.example.beca_android_finalproject.presentation.uimodel.uievents.MovieDetailsUiEvent
import com.example.beca_android_finalproject.presentation.uimodel.uievents.MoviesUiEvent
import com.example.beca_android_finalproject.presentation.viewmodel.MoviesViewModel
import com.example.beca_android_finalproject.ui.theme.OnSurface
import com.example.beca_android_finalproject.ui.theme.Secondary
import com.example.beca_android_finalproject.ui.theme.Surface

@Composable
fun MovieDetailsScreen(
    viewModelFavorite: MoviesViewModel = hiltViewModel(),
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    movieId: Int
) {
    val uiState by viewModel.uiState.collectAsState()
    val favoriteMovies by viewModelFavorite.favoriteMovies.collectAsState()

    var movie by remember { mutableStateOf<Movie?>(null) }

    if (uiState.movieDetails == null && !uiState.isLoading) {
        viewModel.onEvent(MovieDetailsUiEvent.MovieDetails(movieId))
    }

    val isFavorite = favoriteMovies.any { it.id == movieId }

    when {
        uiState.isLoading -> {
            LoadingIndicator()
        }

        uiState.error != null -> {
            ErrorMessage(message = "Error loading movie details: ${uiState.error}")
        }

        uiState.movieDetails != null -> {
            movie = uiState.movieDetails
            Card(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxHeight(),
                colors = CardDefaults.cardColors(
                    containerColor = Surface,
                    contentColor = OnSurface,
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = movie?.title ?: "Movie Title",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(50.dp),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.padding(16.dp))

                    AsyncImage(
                        model = movie?.poster,
                        contentDescription = null,
                    )

                    Spacer(modifier = Modifier.padding(16.dp))

                    val overviewText = movie?.overview?.ifEmpty {
                        "There's no overview for this movie"
                    } ?: "No overview available"

                    Text(
                        overviewText,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(bottom = 8.dp, top = 8.dp, start = 15.dp, end = 15.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.padding(16.dp))

                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isFavorite) "Remove favorite" else "Add favorite",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier,
                            textAlign = TextAlign.Center
                        )
                        IconButton(
                            onClick = {
                                viewModelFavorite.onEvent(MoviesUiEvent.ToggleFavorite(movieId))
                            },
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                        ) {
                            val favoriteIcon = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
                            Icon(
                                imageVector = favoriteIcon,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) Secondary else OnSurface,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
