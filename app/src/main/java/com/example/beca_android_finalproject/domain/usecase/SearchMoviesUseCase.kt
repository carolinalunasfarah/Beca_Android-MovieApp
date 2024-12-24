package com.example.beca_android_finalproject.domain.usecase

import com.example.beca_android_finalproject.domain.model.Movie
import com.example.beca_android_finalproject.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String, page: Int): Flow<List<Movie>> {
        return repository.searchMovies(query, page)
    }
}
