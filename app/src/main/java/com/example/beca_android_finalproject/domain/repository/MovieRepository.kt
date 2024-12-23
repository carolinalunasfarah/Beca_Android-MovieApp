package com.example.beca_android_finalproject.domain.repository

import com.example.beca_android_finalproject.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(page: Int): Flow<List<Movie>>
    suspend fun searchMovies(query: String, page: Int): Flow<List<Movie>>
    suspend fun getMovieDetails(movieId: Int): Flow<Movie>
    suspend fun toggleFavorite(movieId: Int)
    suspend fun getFavorites(): Flow<List<Movie>>
}