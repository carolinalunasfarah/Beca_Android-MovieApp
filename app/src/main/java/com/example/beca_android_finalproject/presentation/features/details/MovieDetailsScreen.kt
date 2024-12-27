package com.example.beca_android_finalproject.presentation.features.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.beca_android_finalproject.domain.model.Movie
import com.example.beca_android_finalproject.presentation.features.composables.ErrorMessage
import com.example.beca_android_finalproject.presentation.features.composables.LoadingIndicator
import com.example.beca_android_finalproject.presentation.uimodel.uievents.MovieDetailsUiEvent
import com.example.beca_android_finalproject.presentation.viewmodel.MoviesViewModel

@Composable
fun MovieDetailsScreen(
    viewModelFavorite: MoviesViewModel = hiltViewModel(),
    movieId: Int
) {
    val uiState by viewModelFavorite.uiState.collectAsState()
    val movie = uiState.movies.firstOrNull { it.id == movieId }

    if (movie != null) {
        Card(
            modifier = Modifier.padding(8.dp)
        ) {
            Column {
                Text(movie.title)
                Spacer(modifier = Modifier.padding(16.dp))
                AsyncImage(
                    model = movie.poster,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(movie.overview)
                Spacer(modifier = Modifier.padding(16.dp))
                IconButton(onClick = {
                    viewModelFavorite.toggleFavorite(movie.id)
                }) {
                    val favoriteIcon = if (movie.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
                    Icon(imageVector = favoriteIcon, contentDescription = "Favorite")
                }
            }
        }
    }
}
/*


@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    viewModelFavorite : MoviesViewModel = hiltViewModel(),
    movieId: Int,

) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.onEvent(MovieDetailsUiEvent.MovieDetails(movieId))

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
                val movie = uiState.movies.firstOrNull()
                if (movie != null) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Column {
                            Text(movie.title)
                            Spacer(modifier = Modifier.padding(16.dp))
                            AsyncImage(
                                model = movie.poster,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.padding(16.dp))
                            Text(movie.overview)
                            Spacer(modifier = Modifier.padding(16.dp))
                            IconButton(onClick = {
                                viewModelFavorite.toggleFavorite(movieId)
                            }) {
                                val favoriteIcon = if (movie.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
                                Icon(imageVector = favoriteIcon, contentDescription = "Favorite")
                            }
                        }
                    }
                }
            }
        }
    }
}*/
