package com.example.beca_android_finalproject.presentation.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beca_android_finalproject.domain.usecase.SearchMoviesUseCase
import com.example.beca_android_finalproject.presentation.uimodel.MoviesUiState
import com.example.beca_android_finalproject.presentation.uimodel.uievents.SearchUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: SearchUiEvent) {
        when {
            event is SearchUiEvent.SearchMovies -> searchMovies(event.query, event.page)
        }
    }

    private fun searchMovies(query: String, page: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                searchMoviesUseCase(query, page)
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
    }

}