package com.example.beca_android_finalproject.data.local.datasource

import com.example.beca_android_finalproject.data.local.dao.MovieDao
import com.example.beca_android_finalproject.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {
    fun getFavorites(): Flow<List<MovieEntity>> = movieDao.getFavorites()

    suspend fun insertMovies(movies: List<MovieEntity>) {
        movieDao.insertMovies(movies)
    }

    suspend fun updateFavorite(movieId: Int, isFavorite: Boolean) {
        movieDao.updateFavorite(movieId, isFavorite)
    }

    fun isFavorite(movieId: Int): Flow<Boolean> = movieDao.isFavorite(movieId)

    suspend fun getMovieDetails(movieId: Int): MovieEntity? {
        return movieDao.getMovieById(movieId)
    }
}
