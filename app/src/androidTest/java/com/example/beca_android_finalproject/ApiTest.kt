package com.example.beca_android_finalproject

import com.example.beca_android_finalproject.data.remote.api.MovieApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiTest {

    private val movieApi: MovieApi by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(MovieApi::class.java)
    }

    @Test
    fun fetchpopularmoviesreturnsdata() = runBlocking {
        val response = movieApi.getPopularMovies(1) // Página 1
        assertTrue("La lista de películas no está vacía", response.movies.isNotEmpty())
    }
}
