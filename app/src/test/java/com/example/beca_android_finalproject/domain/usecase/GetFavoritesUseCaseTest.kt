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

class GetFavoritesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getFavoritesUseCase: GetFavoritesUseCase

    @Before
    fun setUp() {
        movieRepository = mockk()
        getFavoritesUseCase = GetFavoritesUseCase(movieRepository)
    }

    @Test
    fun `invoke should return list of favorite movies`() = runTest {
        // Given
        val favoriteMovies = listOf(
            Movie(
                id = 1,
                title = "Favorite Movie 1",
                overview = "Overview 1",
                poster = "poster1.jpg",
                isFavorite = true
            ),
            Movie(
                id = 2,
                title = "Favorite Movie 2",
                overview = "Overview 2",
                poster = "poster2.jpg",
                isFavorite = true
            ),
            Movie(
                id = 3,
                title = "Non-Favorite Movie",
                overview = "Overview 3",
                poster = "poster3.jpg",
                isFavorite = false
            )
        )
        coEvery { movieRepository.getFavorites() } returns flowOf(favoriteMovies)

        // When
        val result = getFavoritesUseCase()

        // Then
        result.collect { movies ->
            assertEquals(favoriteMovies, movies)
        }
    }
}
