package com.calyrsoft.ucbp1.features.movie.domain.usercase

import com.calyrsoft.ucbp1.features.movie.domain.model.Movie
import com.calyrsoft.ucbp1.features.movie.domain.repository.IMovieRepository

class GetSavedMoviesUseCase(
    private val repository: IMovieRepository
) {
    suspend operator fun invoke(): List<Movie> {
        return repository.getAllMoviesOrderedByRating()
    }
}