package com.example.beca_android_finalproject.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.beca_android_finalproject.data.local.dao.MovieDao
import com.example.beca_android_finalproject.data.local.entities.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)

abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}