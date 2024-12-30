package com.example.beca_android_finalproject.presentation.uimodel.uievents

sealed class MoviesUiEvent {
    data class ToggleFavorite(val movieId: Int) : MoviesUiEvent()
    data object LoadMore : MoviesUiEvent()
    data object GetFavorites : MoviesUiEvent()
}