package com.example.beca_android_finalproject.di

import android.content.Context
import androidx.room.Room
import com.example.beca_android_finalproject.data.local.dao.MovieDao
import com.example.beca_android_finalproject.data.local.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context = context,
            MoviesDatabase::class.java,
            "Movies_database"
        ).build()
    }

    @Provides
    fun provideTaskDao(dataBase: MoviesDatabase) : MovieDao {
        return dataBase.movieDao()
    }
}