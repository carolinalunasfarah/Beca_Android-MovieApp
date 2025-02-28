package com.example.beca_android_finalproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beca_android_finalproject.domain.model.Movie
import com.example.beca_android_finalproject.domain.usecase.GetFavoritesUseCase
import com.example.beca_android_finalproject.domain.usecase.GetPopularMoviesUseCase
import com.example.beca_android_finalproject.domain.usecase.ToggleFavoriteUseCase
import com.example.beca_android_finalproject.presentation.uimodel.uievents.MoviesUiEvent
import com.example.beca_android_finalproject.presentation.uimodel.MoviesUiState
import com.example.beca_android_finalproject.utils.ConnectivityObserver
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
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val connectivityObserver: ConnectivityObserver
    ) : ViewModel() {

    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMovies = _favoriteMovies.asStateFlow()

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState = _uiState.asStateFlow()

    private val _isConnected = MutableStateFlow(true)
    val isConnected = _isConnected.asStateFlow()

    private var currentPage = 1

    init {
        viewModelScope.launch {
            connectivityObserver.isConnected.collect { isConnected ->
                _isConnected.value = isConnected
                if (!isConnected) {
                    loadFavoriteMovies()
                }
                if (isConnected) {
                    loadMovies()
                    loadFavoriteMovies()
                }
            }
        }
    }

    fun onEvent(event: MoviesUiEvent) {
        when (event) {
            is MoviesUiEvent.ToggleFavorite -> toggleFavorite(event.movieId)
            is MoviesUiEvent.LoadMore -> {
                if (!_uiState.value.isLoading) {
                    loadMovies()
                }
            }
            is MoviesUiEvent.GetFavorites -> loadFavoriteMovies()
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                getPopularMoviesUseCase(currentPage).collect { movies ->
                    val favoriteIds = _favoriteMovies.value.map { it.id }.toSet()
                    val updatedMovies = movies.map { movie ->
                        movie.copy(isFavorite = favoriteIds.contains(movie.id))
                    }

                    _uiState.update {
                        it.copy(
                            movies = it.movies + updatedMovies,
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


    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(movieId)

                val updatedMovies = _uiState.value.movies.map { movie ->
                    if (movie.id == movieId) {
                        val updatedMovie = movie.copy(isFavorite = !movie.isFavorite)
                        if (updatedMovie.isFavorite) {
                            _favoriteMovies.update { it + updatedMovie }
                        } else {
                            _favoriteMovies.update { it.filter { it.id != movieId } }
                        }
                        updatedMovie
                    } else {
                        movie
                    }
                }

                _uiState.update { it.copy(movies = updatedMovies) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }


    fun getFavoriteMovies(): List<Movie> {
        return _uiState.value.movies.filter { it.isFavorite }
    }

    private fun loadFavoriteMovies() {
        viewModelScope.launch {
            try {
                getFavoritesUseCase().collect { movies ->
                    _favoriteMovies.value = movies
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}