package com.example.beca_android_finalproject.data.remote.datasource

import com.example.beca_android_finalproject.data.remote.api.MovieApi
import com.example.beca_android_finalproject.data.remote.api.dto.MovieDto
import com.example.beca_android_finalproject.data.remote.api.dto.MovieResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: MovieApi
) {

    suspend fun getPopularMovies(page: Int): MovieResponse {
        return api.getPopularMovies(page)
    }

    suspend fun searchMovies(query: String, page: Int): MovieResponse {
        return api.searchMovies(query, page)
    }

    suspend fun getMovieDetails(movieId: Int): MovieDto {
        return api.getMovieDetails(movieId)
    }
}
