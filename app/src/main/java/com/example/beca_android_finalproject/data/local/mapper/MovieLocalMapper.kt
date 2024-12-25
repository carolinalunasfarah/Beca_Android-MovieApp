package com.example.beca_android_finalproject.data.local.mapper

import com.example.beca_android_finalproject.data.local.entities.MovieEntity
import com.example.beca_android_finalproject.domain.model.Movie
import javax.inject.Inject

class MovieLocalMapper @Inject constructor () {
    fun toDomain(entity: MovieEntity): Movie =
        Movie(
            id = entity.id,
            overview = entity.overview,
            poster = entity.posterPath ?: "",
            title = entity.title
        )

    fun toEntity(domain: Movie): MovieEntity =
        MovieEntity(
            id = domain.id,
            overview = domain.overview,
            posterPath = domain.poster ?: "",
            title = domain.title
        )

    fun toDomainList(entities: List<MovieEntity>): List<Movie> {
        return entities.map { toDomain(it) }
    }

    fun toEntityList(domains: List<Movie>): List<MovieEntity> {
        return domains.map { toEntity(it) }
    }
}