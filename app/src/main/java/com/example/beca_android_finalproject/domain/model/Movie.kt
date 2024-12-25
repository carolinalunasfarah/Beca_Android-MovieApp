package com.example.beca_android_finalproject.domain.model

data class Movie (
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String?,
    val isFavorite: Boolean = false
)
