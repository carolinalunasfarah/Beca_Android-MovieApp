package com.example.beca_android_finalproject.domain.usecase

import com.example.beca_android_finalproject.domain.repository.MovieRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) {
        repository.toggleFavorite(movieId)
    }
}
