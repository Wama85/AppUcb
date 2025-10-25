package com.calyrsoft.ucbp1.features.movie.domain.usercase

import com.calyrsoft.ucbp1.features.movie.domain.model.Movie
import com.calyrsoft.ucbp1.features.movie.domain.repository.IMovieRepository

class GetWatchLaterMoviesUseCase(
    private val repository: IMovieRepository
) {
    suspend operator fun invoke(): List<Movie> {
        return repository.getWatchLaterMovies()
    }
}
