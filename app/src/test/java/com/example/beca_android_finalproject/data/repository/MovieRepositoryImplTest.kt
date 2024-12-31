package com.example.beca_android_finalproject.data.repository

import com.example.beca_android_finalproject.data.local.datasource.LocalDataSource
import com.example.beca_android_finalproject.data.local.entities.MovieEntity
import com.example.beca_android_finalproject.data.local.mapper.MovieLocalMapper
import com.example.beca_android_finalproject.data.remote.api.dto.MovieDto
import com.example.beca_android_finalproject.data.remote.api.dto.MovieResponse
import com.example.beca_android_finalproject.data.remote.api.mapper.MovieRemoteMapper
import com.example.beca_android_finalproject.data.remote.datasource.RemoteDataSource
import com.example.beca_android_finalproject.domain.model.Movie
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After

class MovieRepositoryImplTest {

    private lateinit var movieRepository: MovieRepositoryImpl

    @MockK
    private lateinit var remoteDataSource: RemoteDataSource

    @MockK
    private lateinit var localDataSource: LocalDataSource

    @MockK
    private lateinit var movieRemoteMapper: MovieRemoteMapper

    @MockK
    private lateinit var movieLocalMapper: MovieLocalMapper

    @MockK
    private lateinit var dispatcher: CoroutineDispatcher

    private val testMovie = Movie(
        id = 1,
        title = "Test Movie",
        overview = "Test Overview",
        poster = "test_poster_path",
    )

    private val testMovieEntity = MovieEntity(
        id = 1,
        title = "Test Movie",
        isFavorite = false,
        overview = "Test Overview",
        posterPath = "test_poster_path",
    )

    private val testMovieDto = MovieDto(
        id = 1,
        title = "Test Movie",
        overview = "Test Overview",
        posterPath = "test_poster_path",
        adult = false,
        backdropPath = "test_backDrop_path",
        genreIds = listOf(1,2,3,4),
        originalLanguage = "en",
        originalTitle = "Test Movie Original Title",
        popularity = 5.0,
        releaseDate = "2024-12-29",
        video = false,
        voteAverage = 5.0,
        voteCount = 5
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(dispatcher)
        movieRepository = MovieRepositoryImpl(
            remoteDataSource,
            localDataSource,
            movieRemoteMapper,
            movieLocalMapper,
            dispatcher
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // getPopularMovies
    @Test
    fun `Given popular movies and local favorites, Then return mapped movies and sync favorites`() = runTest {
        // Given
        val page = 1
        val movieResponse = MovieResponse(
            page = 1,
            movies = listOf(testMovieDto),
            totalPages = 1,
            totalResults = 1
        )
        val localFavorites = listOf(testMovieEntity)

        coEvery { remoteDataSource.getPopularMovies(page) } returns movieResponse
        coEvery { localDataSource.getFavorites() } returns flowOf(localFavorites)
        coEvery { movieRemoteMapper.toEntity(any()) } returns testMovieEntity
        coEvery { localDataSource.insertMovies(any()) } just Runs
        coEvery { movieRemoteMapper.toDomainList(any()) } returns listOf(testMovie)

        // When
        val result = movieRepository.getPopularMovies(page).first()

        // Then
        assertEquals(listOf(testMovie), result)
        coVerify { remoteDataSource.getPopularMovies(page) }
        coVerify { localDataSource.getFavorites() }
        coVerify { localDataSource.insertMovies(any()) }
    }


    // searchMovies
    @Test
    fun `Given a search query and remote movies, Then return mapped movies`() = runTest {
        // Given
        val query = "Test"
        val page = 1
        val movieResponse = MovieResponse(
            page = 1,
            movies = listOf(testMovieDto),
            totalPages = 1,
            totalResults = 1
        )

        coEvery { remoteDataSource.searchMovies(query, page) } returns movieResponse
        coEvery { movieRemoteMapper.toDomain(any()) } returns testMovie

        // When
        val result = movieRepository.searchMovies(query, page).first()

        // Then
        assertEquals(listOf(testMovie), result)
        coVerify { remoteDataSource.searchMovies(query, page) }
    }

    // getMovieDetails
    @Test
    fun `Given a movie ID, Then return the mapped movie details`() = runTest {
        // Given
        val movieId = 1
        coEvery { remoteDataSource.getMovieDetails(movieId) } returns testMovieDto
        coEvery { movieRemoteMapper.toDomain(any()) } returns testMovie

        // When
        val result = movieRepository.getMovieDetails(movieId).first()

        // Then
        assertEquals(testMovie, result)
        coVerify { remoteDataSource.getMovieDetails(movieId) }
    }

    // toggleFavorite
    @Test
    fun `Given a movie ID and current favorite status, Then update the favorite status`() = runTest {
        // Given
        val movieId = 1
        val isFavorite = true

        coEvery { localDataSource.isFavorite(movieId) } returns flowOf(isFavorite)
        coEvery { localDataSource.updateFavorite(movieId, !isFavorite) } just Runs

        // When
        movieRepository.toggleFavorite(movieId)

        // Then
        coVerify { localDataSource.updateFavorite(movieId, !isFavorite) }
    }

    // getFavorites
    @Test
    fun `Given favorite movies in the local data source, Then return the list of favorite movies`() = runTest{
        // Given
        val favoriteMovies = listOf(testMovieEntity)

        coEvery { localDataSource.getFavorites() } returns flowOf(favoriteMovies)
        coEvery { movieLocalMapper.toDomainList(any()) } returns listOf(testMovie)

        // When
        val result = movieRepository.getFavorites().first()

        // Then
        assertEquals(listOf(testMovie), result)
        coVerify { localDataSource.getFavorites() }
    }

}
