package com.example.beca_android_finalproject.data.remote.api

interface MovieApi {
    @GET("movie/popular")
    suspend fun getPopularMovies (
        @Query ("page") page: Int
    ) : MovieResponse

    @GET("search/movie")
    suspend fun searchMovies (
        @Query ("query") query: String,
        @Query ("page") page: Int
    ) : MovieResponse
}