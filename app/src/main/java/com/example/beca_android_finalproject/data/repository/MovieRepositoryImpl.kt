package com.example.beca_android_finalproject.data.repository

import com.example.beca_android_finalproject.data.local.datasource.LocalDataSource
import com.example.beca_android_finalproject.data.local.mapper.MovieLocalMapper
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
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val movieRemoteMapper: MovieRemoteMapper,
    private val movieLocalMapper: MovieLocalMapper,
    private val dispatcher: CoroutineDispatcher
) : MovieRepository {

    override fun getPopularMovies(page: Int): Flow<List<Movie>> = flow {
        val localFavorites = localDataSource.getFavorites().first()

        try {
            val remoteMovies = remoteDataSource.getPopularMovies(page).movies
            val moviesEntities = remoteMovies.map { movieRemoteMapper.toEntity(it) }

            val syncedMovies = moviesEntities.map { movieEntity ->
                movieEntity.copy(
                    isFavorite = localFavorites.any { it.id == movieEntity.id }
                )
            }

            localDataSource.insertMovies(syncedMovies)

            emit(movieRemoteMapper.toDomainList(remoteMovies))
        } catch (e: Exception) {
            throw e
        }
    }.flowOn(dispatcher)



    override suspend fun searchMovies(query: String, page: Int): Flow<List<Movie>> = flow {
        try {
            val remoteMovies = remoteDataSource.searchMovies(query, page).movies

            val movieEntities = movieRemoteMapper.toEntityList(remoteMovies)
            localDataSource.insertMovies(movieEntities)

            emit(remoteMovies.map { movieRemoteMapper.toDomain(it) })
        } catch (e: Exception) {
            throw e
        }
    }.flowOn(dispatcher)


    override suspend fun getMovieDetails(movieId: Int): Flow<Movie> = flow {
        try {
            val localMovie = localDataSource.getMovieDetails(movieId)

            if (localMovie != null) {
                emit(movieLocalMapper.toDomain(localMovie))
            }

            val remoteMovie = remoteDataSource.getMovieDetails(movieId)
            emit(movieRemoteMapper.toDomain(remoteMovie))
        } catch (e: Exception) {
            throw e
        }
    }.flowOn(dispatcher)




    override suspend fun toggleFavorite(movieId: Int) {
        withContext(dispatcher) {
            try {
                val isFavorite = localDataSource.isFavorite(movieId).first()
                localDataSource.updateFavorite(movieId, !isFavorite)
            } catch (e: Exception) {
                throw e
            }
        }
    }


    override suspend fun getFavorites(): Flow<List<Movie>> = flow {
        emitAll(localDataSource.getFavorites().map { entities ->
            movieLocalMapper.toDomainList(entities)
        })
    }.flowOn(dispatcher)
}

