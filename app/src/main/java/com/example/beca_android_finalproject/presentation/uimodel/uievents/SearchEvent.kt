package com.example.beca_android_finalproject.presentation.uimodel.uievents

sealed class SearchUiEvent {
    data class SearchMovies(val query: String, val page: Int) : SearchUiEvent()
}