package com.example.beca_android_finalproject.data.remote.api.mapper

import com.example.beca_android_finalproject.data.remote.api.dto.MovieDto
import com.example.beca_android_finalproject.domain.model.Movie

class MovieMapper {

    fun toDomain(dto: MovieDto): Movie =
        Movie(
            id = dto.id,
            overview = dto.overview,
            posterPath = dto.posterPath ?: "",
            title = dto.title
        )
}