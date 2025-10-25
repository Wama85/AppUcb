package com.calyrsoft.ucbp1.features.movie.domain.usercase

import com.calyrsoft.ucbp1.features.movie.domain.repository.IMovieRepository

class UpdateMovieRatingUseCase(
    private val repository: IMovieRepository
) {
    suspend operator fun invoke(movieId: Int, rating: Float) {
        repository.updateMovieRating(movieId, rating)
    }
}