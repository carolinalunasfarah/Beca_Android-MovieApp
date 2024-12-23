package com.example.beca_android_finalproject.di

import android.content.Context
import androidx.room.Room
import com.example.beca_android_finalproject.data.local.dao.MovieDao
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
    fun provideDatabase(@ApplicationContext context: Context): MoviesDataBase {
        return Room.databaseBuilder(
            context = context,
            MoviesDataBase::class.java,
            "Movies_database"
        ).build()
    }

    @Provides
    fun provideTaskDao(dataBase: MovieDataBase) : MovieDao {
        return dataBase.movieDao()
    }
}