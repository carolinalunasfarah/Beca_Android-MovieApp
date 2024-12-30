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

class SearchMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var searchMoviesUseCase: SearchMoviesUseCase

    @Before
    fun setUp() {
        movieRepository = mockk()
        searchMoviesUseCase = SearchMoviesUseCase(movieRepository)
    }

    @Test
    fun `invoke should return list of movies matching query`() = runTest {
        // Given
        val query = "Inception"
        val page = 1
        val searchResults = listOf(
            Movie(
                id = 1,
                title = "Inception",
                overview = "A mind-bending thriller",
                poster = "poster1.jpg",
                isFavorite = false
            ),
            Movie(
                id = 2,
                title = "Inception 2",
                overview = "A sequel to the mind-bending thriller",
                poster = "poster2.jpg",
                isFavorite = false
            )
        )
        coEvery { movieRepository.searchMovies(query, page) } returns flowOf(searchResults)

        // When
        val result = searchMoviesUseCase(query, page)

        // Then
        result.collect { movies ->
            assertEquals(searchResults, movies)
        }
    }
}
