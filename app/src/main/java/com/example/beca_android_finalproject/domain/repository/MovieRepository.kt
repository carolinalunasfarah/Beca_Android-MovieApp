package com.example.beca_android_finalproject.domain.repository

import com.example.beca_android_finalproject.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(page: Int): Flow<List<Movie>>
    suspend fun toggleFavorite(movieId: Int)
}