package com.example.beca_android_finalproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beca_android_finalproject.presentation.uimodel.MoviesUiEvent
import com.example.beca_android_finalproject.presentation.uimodel.MoviesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState = _uiState.asStateFlow()

    private var currentPage = 1

    init {
        loadMovies()
    }

    fun onEvent(event: MoviesUiEvent) {
        when (event) {
            is MoviesUiEvent.ToggleFavorite -> toggleFavorite(event.movieId)
            is MoviesUiEvent.SearchMovies -> searchMovies(event.query)
            is MoviesUiEvent.LoadMore -> loadMovies()
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                getPopularMoviesUseCase(currentPage)
                    .collect { movies ->
                        _uiState.update {
                            it.copy(
                                movies = movies,
                                isLoading = false
                            )
                        }
                    }
                currentPage++
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }

            }
        }
    }
}