package com.example.beca_android_finalproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beca_android_finalproject.domain.usecase.GetMoviesDetailsUseCase
import com.example.beca_android_finalproject.domain.usecase.GetPopularMoviesUseCase
import com.example.beca_android_finalproject.domain.usecase.ToggleFavoriteUseCase
import com.example.beca_android_finalproject.presentation.uimodel.uievents.MoviesUiEvent
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
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val getMovieDetailsUseCase: GetMoviesDetailsUseCase,
    //private val searchMovieUseCase: SearchMoviesUseCase
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
            //is MoviesUiEvent.SearchMovies -> searchMovies(event.query, event.page)
            is MoviesUiEvent.LoadMore -> loadMovies()
            is MoviesUiEvent.MovieDetails -> loadMovieDetails(event.movieId)
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
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                currentPage++
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }

            }
        }
    }

    private fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(movieId)
                val updatedMovies = _uiState.value.movies.map { movie ->
                    if (movie.id == movieId) movie.copy(isFavorite = !movie.isFavorite) else movie
                }
                _uiState.update { it.copy(movies = updatedMovies) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    /*private fun searchMovies(query: String, page: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                searchMovieUseCase(query, page)
                    .collect { movies ->
                        _uiState.update {
                            it.copy(
                                movies = movies,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }*/

    private fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                getMovieDetailsUseCase(movieId)
                    .collect {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = null
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}