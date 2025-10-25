package com.calyrsoft.ucbp1.features.movie.data.repository

import com.calyrsoft.ucbp1.features.movie.data.database.dao.IMovieDao
import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity
import com.calyrsoft.ucbp1.features.movie.domain.model.Movie
import com.calyrsoft.ucbp1.features.movie.domain.repository.IMovieRepository

class MovieLocalRepository(
    private val movieDao: IMovieDao
) : IMovieRepository {

    override suspend fun saveMovie(movie: Movie) {
        val entity = MovieEntity(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterPath = movie.posterPath,
            backdropPath = movie.backdropPath,
            releaseDate = movie.releaseDate,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
            userRating = movie.rating,
            watchLater = movie.watchLater
        )
        movieDao.insert(entity)
    }

    override suspend fun updateMovieRating(movieId: Int, rating: Float) {
        movieDao.updateRating(movieId, rating)
    }

    override suspend fun updateWatchLater(movieId: Int, watchLater: Boolean) {
        movieDao.updateWatchLater(movieId, watchLater)
    }

    override suspend fun getAllMoviesOrderedByRating(): List<Movie> {
        return movieDao.getAllOrderedByRating().map { it.toDomain() }
    }

    override suspend fun getMovieById(movieId: Int): Movie? {
        return movieDao.getById(movieId)?.toDomain()
    }

    override suspend fun getWatchLaterMovies(): List<Movie> {
        return movieDao.getWatchLaterMovies().map { it.toDomain() }
    }

    private fun MovieEntity.toDomain(): Movie {
        return Movie(
            id = this.id,
            title = this.title,
            overview = this.overview,
            posterPath = this.posterPath,
            backdropPath = this.backdropPath,
            releaseDate = this.releaseDate,
            voteAverage = this.voteAverage,
            voteCount = this.voteCount,
            rating = this.userRating,
            watchLater = this.watchLater
        )
    }
}
