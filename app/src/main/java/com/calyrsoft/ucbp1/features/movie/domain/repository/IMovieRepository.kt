package com.calyrsoft.ucbp1.features.movie.domain.repository

import com.calyrsoft.ucbp1.features.movie.domain.model.Movie

interface IMovieRepository {
    suspend fun saveMovie(movie: Movie)
    suspend fun updateMovieRating(movieId: Int, rating: Float)
    suspend fun updateWatchLater(movieId: Int, watchLater: Boolean)
    suspend fun getAllMoviesOrderedByRating(): List<Movie>
    suspend fun getMovieById(movieId: Int): Movie?
    suspend fun getWatchLaterMovies(): List<Movie>
}
