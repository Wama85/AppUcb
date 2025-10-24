package com.calyrsoft.ucbp1.features.movie.data.datasource.remote

import com.calyrsoft.ucbp1.BuildConfig
import com.calyrsoft.ucbp1.features.movie.data.api.MovieService
import com.calyrsoft.ucbp1.features.movie.data.api.dto.MovieDto
import com.calyrsoft.ucbp1.features.movie.data.api.dto.MoviesResponseDto

class MovieRemoteDataSource(
    private val movieService: MovieService
) {
    suspend fun getPopularMovies(): Result<List<MovieDto>> {
        return try {
            println("🎬 DEBUG: Iniciando llamada a TMDB API")
            val response = movieService.getPopularMovies(apiKey = BuildConfig.TMDB_API_KEY)
            println("🎬 DEBUG: Response code: ${response.code()}")
            println("🎬 DEBUG: Response body: ${response.body()}")

            if (response.isSuccessful && response.body() != null) {
                val movies = response.body()!!.results
                println("🎬 DEBUG: Películas obtenidas: ${movies.size}")
                Result.success(movies)
            } else {
                val error = "Error ${response.code()}: ${response.errorBody()?.string()}"
                println("🎬 DEBUG ERROR: $error")
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            println("🎬 DEBUG EXCEPTION: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }
}