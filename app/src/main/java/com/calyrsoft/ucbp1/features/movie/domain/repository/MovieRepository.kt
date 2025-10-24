package com.calyrsoft.ucbp1.features.movie.domain.repository

import com.calyrsoft.ucbp1.features.movie.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(): Result<List<Movie>>
}