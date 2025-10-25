package com.calyrsoft.ucbp1.features.movie.domain.usercase

import com.calyrsoft.ucbp1.features.movie.domain.repository.IMovieRepository

class ToggleWatchLaterUseCase(
    private val repository: IMovieRepository
) {
    suspend operator fun invoke(movieId: Int, watchLater: Boolean) {
        repository.updateWatchLater(movieId, watchLater)
    }
}
