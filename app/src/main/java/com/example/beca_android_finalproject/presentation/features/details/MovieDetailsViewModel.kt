package com.example.beca_android_finalproject.presentation.features.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beca_android_finalproject.domain.usecase.GetMoviesDetailsUseCase
import com.example.beca_android_finalproject.domain.usecase.ToggleFavoriteUseCase
import com.example.beca_android_finalproject.presentation.uimodel.MoviesUiState
import com.example.beca_android_finalproject.presentation.uimodel.uievents.MovieDetailsUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMoviesDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: MovieDetailsUiEvent) {
        when (event) {
            is MovieDetailsUiEvent.MovieDetails -> loadMovieDetails(event.movieId)
            is MovieDetailsUiEvent.ToggleFavorite -> toggleFavorite(event.movieId)
        }
    }

    private fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                getMovieDetailsUseCase(movieId)
                    .collect { movieDetails ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = null,
                                movies = listOf(movieDetails)
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message, isLoading = false)
                }
            }
        }
    }

    private fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(movieId)
                _uiState.update { currentState ->
                    val updatedMovies = currentState.movies.map { movie ->
                        if (movie.id == movieId) movie.copy(isFavorite = !movie.isFavorite) else movie
                    }
                    currentState.copy(movies = updatedMovies)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}