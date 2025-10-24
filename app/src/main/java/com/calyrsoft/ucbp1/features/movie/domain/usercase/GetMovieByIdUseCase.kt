package com.calyrsoft.ucbp1.features.movie.domain.usercase

import com.calyrsoft.ucbp1.features.movie.domain.model.Movie
import com.calyrsoft.ucbp1.features.movie.domain.repository.MovieRepository

class GetMovieByIdUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Result<Movie> {
        return repository.getMovieById(movieId)
    }
}