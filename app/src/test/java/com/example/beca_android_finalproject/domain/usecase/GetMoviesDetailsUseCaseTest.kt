package com.example.beca_android_finalproject.domain.usecase

import com.example.beca_android_finalproject.domain.model.Movie
import com.example.beca_android_finalproject.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetMoviesDetailsUseCaseTest {

    private lateinit var repository: MovieRepository
    private lateinit var getMoviesDetailsUseCase: GetMoviesDetailsUseCase

    @Before
    fun setUp() {
        repository = mockk()
        getMoviesDetailsUseCase = GetMoviesDetailsUseCase(repository)
    }

    @Test
    fun `invoke should return movie details for given id`() = runTest {
        // Given
        val movieId = 1
        val movie = Movie(
            id = movieId,
            title = "Test Movie",
            overview = "Overview 1.",
            poster = "poster.jpg",
            isFavorite = false
        )
        coEvery { repository.getMovieDetails(movieId) } returns flowOf(movie)

        // When
        val result = getMoviesDetailsUseCase(movieId)

        // Then
        result.collect { movieDetails ->
            assertEquals(movie, movieDetails)
        }
    }
}