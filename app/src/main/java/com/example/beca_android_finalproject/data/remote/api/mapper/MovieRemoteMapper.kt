package com.example.beca_android_finalproject.data.remote.api.mapper

import com.example.beca_android_finalproject.data.local.entities.MovieEntity
import com.example.beca_android_finalproject.data.remote.api.dto.MovieDto
import com.example.beca_android_finalproject.domain.model.Movie
import javax.inject.Inject

class MovieRemoteMapper @Inject constructor() {

    fun toDomain(dto: MovieDto): Movie =
        Movie(
            id = dto.id,
            overview = dto.overview,
            poster = dto.posterPath?.let {
                "https://image.tmdb.org/t/p/w500$it"
            },
            title = dto.title,
        )

    fun toEntity(dto: MovieDto): MovieEntity =
        MovieEntity(
            id = dto.id,
            title = dto.title,
            overview = dto.overview,
            posterPath = dto.posterPath,
            isFavorite = false
        )

    fun toDomainList(entities: List<MovieDto>): List<Movie> {
        return entities.map { toDomain(it) }
    }

    fun toEntityList(remoteMovies: List<MovieDto>): List<MovieEntity> {
        return remoteMovies.map { toEntity(it) }
    }
}
