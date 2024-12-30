package com.example.beca_android_finalproject.domain.usecase

import com.example.beca_android_finalproject.domain.repository.MovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ToggleFavoriteUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    @Before
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        toggleFavoriteUseCase = ToggleFavoriteUseCase(movieRepository)
    }

    @Test
    fun `invoke should call toggleFavorite on repository with correct movieId`() = runTest {
        // Given
        val movieId = 42

        // When
        toggleFavoriteUseCase(movieId)

        // Then
        coVerify { movieRepository.toggleFavorite(movieId) }
    }
}
