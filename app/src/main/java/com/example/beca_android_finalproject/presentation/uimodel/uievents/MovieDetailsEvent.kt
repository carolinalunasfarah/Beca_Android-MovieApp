package com.example.beca_android_finalproject.presentation.uimodel.uievents

sealed class MovieDetailsUiEvent {
    data class MovieDetails(val movieId: Int) : MovieDetailsUiEvent()
}