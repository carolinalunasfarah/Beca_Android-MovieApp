package com.example.beca_android_finalproject.presentation.features.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.beca_android_finalproject.domain.model.Movie
import com.example.beca_android_finalproject.ui.theme.LightBackground
import com.example.beca_android_finalproject.ui.theme.LightOnBackground
import com.example.beca_android_finalproject.ui.theme.LightOnSurface
import com.example.beca_android_finalproject.ui.theme.LightPrimary
import com.example.beca_android_finalproject.ui.theme.LightSurface

@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit,
    onFavoriteClick: (Int) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = LightSurface,
            contentColor = LightOnSurface,
        ),
        modifier = Modifier
            .padding(8.dp)
            .height(400.dp)
            .clickable { onClick() },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.padding(8.dp))

            AsyncImage(
                model = movie.poster,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Row (
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Add as favorite",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
                IconButton(
                    onClick = { onFavoriteClick(movie.id) },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                ) {
                    val favoriteIcon = if (movie.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
                    Icon(
                        imageVector = favoriteIcon,
                        contentDescription = "Favorite",
                        tint = if (movie.isFavorite) LightOnBackground else LightOnSurface,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    }
}

/*@Composable
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
}*/