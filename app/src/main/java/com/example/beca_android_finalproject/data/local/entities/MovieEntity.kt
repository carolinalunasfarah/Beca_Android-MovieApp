package com.example.beca_android_finalproject.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name= "title")
    val title: String,

    @ColumnInfo(name= "overview")
    val overview: String,

    @ColumnInfo(name= "poster_path")
    val posterPath: String,

    @ColumnInfo(name= "is_favorite")
    val isFavorite: Boolean = false
)