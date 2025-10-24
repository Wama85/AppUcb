package com.calyrsoft.ucbp1.features.movie.data.repository

import com.calyrsoft.ucbp1.features.movie.data.api.dto.MovieDto
import com.calyrsoft.ucbp1.features.movie.data.datasource.remote.MovieRemoteDataSource
import com.calyrsoft.ucbp1.features.movie.domain.model.Movie
import com.calyrsoft.ucbp1.features.movie.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    private var cachedMovies: List<Movie> = emptyList()

    override suspend fun getPopularMovies(): Result<List<Movie>> {
        return remoteDataSource.getPopularMovies().map { movieDtos ->
            movieDtos.map { it.toDomain() }.also { cachedMovies = it }
        }
    }

    override suspend fun getMovieById(movieId: Int): Result<Movie> {
        val movie = cachedMovies.find { it.id == movieId }
        return if (movie != null) {
            Result.success(movie)
        } else {
            Result.failure(Exception("Película no encontrada"))
        }
    }
}

// Extension function to convert DTO to Domain
private fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
        backdropPath = backdropPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        rating = (voteAverage / 2.0).toFloat()
    )
}