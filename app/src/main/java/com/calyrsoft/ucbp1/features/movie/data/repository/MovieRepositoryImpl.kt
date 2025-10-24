package com.calyrsoft.ucbp1.features.movie.data.repository

import com.calyrsoft.ucbp1.features.movie.data.api.dto.MovieDto
import com.calyrsoft.ucbp1.features.movie.data.datasource.remote.MovieRemoteDataSource
import com.calyrsoft.ucbp1.features.movie.domain.model.Movie
import com.calyrsoft.ucbp1.features.movie.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(): Result<List<Movie>> {
        return remoteDataSource.getPopularMovies().map { movieDtos ->
            movieDtos.map { it.toDomain() }
        }
    }
}

// Extension function to convert DTO to Domain
private fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview ?: "",
        posterPath = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
        backdropPath = backdropPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
        releaseDate = releaseDate ?: "",
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        rating = (voteAverage ?: 0.0).toFloat() / 2f  // Convierte de escala 0-10 a 0-5
    )
}