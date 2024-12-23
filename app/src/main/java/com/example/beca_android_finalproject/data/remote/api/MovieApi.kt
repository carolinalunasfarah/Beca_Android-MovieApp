package com.example.beca_android_finalproject.data.remote.api

import com.example.beca_android_finalproject.data.remote.api.dto.MovieDto
import com.example.beca_android_finalproject.data.remote.api.dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getPopularMovies (
        @Query("page") page: Int
    ) : MovieResponse

    @GET("search/movie")
    suspend fun searchMovies (
        @Query ("query") query: String,
        @Query ("page") page: Int
    ) : MovieResponse

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: Int
    ): MovieDto
}