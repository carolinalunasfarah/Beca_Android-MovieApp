package com.example.beca_android_finalproject.presentation.features.components

import androidx.compose.foundation.clickable
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
import com.example.beca_android_finalproject.presentation.viewmodel.MoviesViewModel

@Composable
fun MovieCard(
    viewModel: MoviesViewModel = hiltViewModel(),
    movie: Movie,
    onClick: () -> Unit,
    onFavoriteClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column {
            Text(movie.title)
            Spacer(modifier = Modifier.padding(16.dp))
            AsyncImage(
                model = movie.poster,
                contentDescription = null
            )
            Spacer(modifier = Modifier.padding(16.dp))
            IconButton(onClick = { onFavoriteClick(movie.id) }) {
                val favoriteIcon = if (movie.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
                Icon(imageVector = favoriteIcon, contentDescription = "Favorite")
            }
        }
    }
}

