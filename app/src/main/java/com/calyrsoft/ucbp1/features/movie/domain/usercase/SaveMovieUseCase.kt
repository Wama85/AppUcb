package com.calyrsoft.ucbp1.features.movie.domain.usercase

import com.calyrsoft.ucbp1.features.movie.domain.model.Movie
import com.calyrsoft.ucbp1.features.movie.domain.repository.IMovieRepository

class SaveMovieUseCase(
    private val repository: IMovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        repository.saveMovie(movie)
    }
}