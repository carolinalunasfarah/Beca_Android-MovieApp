package com.example.beca_android_finalproject.data.repository

import android.util.Log
import com.example.beca_android_finalproject.data.local.datasource.LocalDataSource
import com.example.beca_android_finalproject.data.local.mapper.MovieLocalMapper
import com.example.beca_android_finalproject.data.remote.api.MovieApi
import com.example.beca_android_finalproject.data.remote.api.mapper.MovieRemoteMapper
import com.example.beca_android_finalproject.data.remote.datasource.RemoteDataSource
import com.example.beca_android_finalproject.domain.model.Movie
import com.example.beca_android_finalproject.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val movieRemoteMapper: MovieRemoteMapper,
    private val movieLocalMapper: MovieLocalMapper,
    private val dispatcher: CoroutineDispatcher
) : MovieRepository {

    override fun getPopularMovies(page: Int): Flow<List<Movie>> = flow {
        val localMovies = localDataSource.getFavorites().first()
        emit(movieLocalMapper.toDomainList(localMovies))

        try {
            val remoteMovies = remoteDataSource.getPopularMovies(page).movies
            val moviesEntities = remoteMovies.map { movieRemoteMapper.toEntity(it) }
            localDataSource.insertMovies(moviesEntities)

            emit(movieRemoteMapper.toDomainList(remoteMovies))
        } catch (e: Exception) {
            throw e
        }
    }.flowOn(dispatcher)


    override suspend fun searchMovies(query: String, page: Int): Flow<List<Movie>> = flow {
        try {
            val remoteMovies = remoteDataSource.searchMovies(query, page).movies
            emit(remoteMovies.map { movieRemoteMapper.toDomain(it) })
        } catch (e: Exception) {
            throw e
        }
    }.flowOn(dispatcher)


    override suspend fun getMovieDetails(movieId: Int): Flow<Movie> = flow {
            try {
                val movieDto = remoteDataSource.getMovieDetails(movieId)
                emit(movieRemoteMapper.toDomain(movieDto))
            } catch (e: Exception) {
                throw e
            }
    }.flowOn(dispatcher)


    override suspend fun toggleFavorite(movieId: Int) {
        withContext(dispatcher) {
            val isFavorite = localDataSource.isFavorite(movieId).first()
            localDataSource.updateFavorite(movieId, !isFavorite)
        }
    }

    override suspend fun getFavorites(): Flow<List<Movie>> = flow {
        emitAll(localDataSource.getFavorites().map { entities ->
            movieLocalMapper.toDomainList(entities)
        })
    }.flowOn(dispatcher)
}

