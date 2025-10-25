package com.calyrsoft.ucbp1.features.movie.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val rating: Float = 0.0f,
    val watchLater: Boolean = false
) {
    // URL completa para la imagen del poster
    fun getFullPosterUrl(): String {
        return if (posterPath != null) {
            "https://image.tmdb.org/t/p/w500$posterPath"
        } else {
            "" // o una imagen por defecto
        }
    }

    // URL completa para la imagen de fondo
    fun getFullBackdropUrl(): String {
        return if (backdropPath != null) {
            "https://image.tmdb.org/t/p/w780$backdropPath"
        } else {
            "" // o una imagen por defecto
        }
    }
}
