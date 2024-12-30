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

class GetPopularMoviesUseCaseTest {

    private lateinit var repository: MovieRepository
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Before
    fun setUp() {
        repository = mockk()
        getPopularMoviesUseCase = GetPopularMoviesUseCase(repository)
    }

    @Test
    fun `invoke should return list of popular movies`() = runTest {
        // Given
        val page = 1
        val movies = listOf(
            Movie(
                1,
                "Movie 1",
                "Overview 1",
                "poster1.jpg"
            ),
            Movie(
                2,
                "Movie 2",
                "Overview 2",
                "poster2.jpg")
        )

        coEvery { repository.getPopularMovies(page) } returns flowOf(movies)

        // When
        val result = getPopularMoviesUseCase(page)

        // Then
        result.collect { movieList ->
            assertEquals(movies, movieList)
        }
    }
}
