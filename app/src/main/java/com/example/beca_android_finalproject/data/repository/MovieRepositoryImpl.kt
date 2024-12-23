package com.example.beca_android_finalproject.data.repository

import com.example.beca_android_finalproject.data.local.dao.MovieDao
import com.example.beca_android_finalproject.data.remote.api.MovieApi
import com.example.beca_android_finalproject.domain.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val dao: MovieDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository {
    override fun getPopularMovies(page: Int): Flow<List<Movie>> = flow {
        emitAll(dao.getPopularMovies().map { entities ->
            entities.map { it.toDomain() }
        })

        try {
            val remoteMovies = api.getPopularMovies(page).results
            dao.insertMovies(remoteMovies.map { it.toEntity() })
        } catch (e: Exception) {
            //TODO
        }
    }.flowOn(dispatcher)


    override suspend fun toggleFavorite(movieId: Int) {
        withContext(dispatcher) {
            val isFavorite = dao.isFavorite(movieId)
            dao.updateFavorite(movieId, !isFavorite)
        }
    }
}