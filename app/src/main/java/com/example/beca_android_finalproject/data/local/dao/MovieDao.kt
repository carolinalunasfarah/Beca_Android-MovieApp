package com.example.beca_android_finalproject.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.beca_android_finalproject.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getPopularMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Int): MovieEntity?

    @Query("SELECT * FROM movies WHERE is_favorite = 1")
    fun getFavorites(): Flow<List<MovieEntity>>

    @Query("SELECT is_favorite FROM movies WHERE id = :movieId")
    fun isFavorite(movieId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("UPDATE movies SET is_favorite = :isFavorite WHERE id = :movieId")
    suspend fun updateFavorite(movieId: Int, isFavorite: Boolean)
}