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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.beca_android_finalproject.presentation.viewmodel.MoviesViewModel
import com.example.beca_android_finalproject.ui.theme.OnSurface
import com.example.beca_android_finalproject.ui.theme.Secondary
import com.example.beca_android_finalproject.ui.theme.Surface

@Composable
fun MovieDetailsScreen(
    viewModelFavorite: MoviesViewModel = hiltViewModel(),
    movieId: Int
) {
    val uiState by viewModelFavorite.uiState.collectAsState()
    val movie = uiState.movies.firstOrNull { it.id == movieId }

    if (movie != null) {
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
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(16.dp))

                AsyncImage(
                    model = movie.poster,
                    contentDescription = null,
                    //modifier = Modifier
                    //  .fillMaxWidth()
                )

                Spacer(modifier = Modifier.padding(16.dp))

                Text(
                    movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(bottom = 8.dp, top = 8.dp, start = 15.dp, end = 15.dp)
                        .fillMaxWidth(),
                    //.height(50.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(16.dp))

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add as favorite",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        onClick = {
                            viewModelFavorite.toggleFavorite(movie.id)
                        },
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    ) {
                        val favoriteIcon =
                            if (movie.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
                        Icon(
                            imageVector = favoriteIcon,
                            contentDescription = "Favorite",
                            tint = if (movie.isFavorite) Secondary else OnSurface,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }
}