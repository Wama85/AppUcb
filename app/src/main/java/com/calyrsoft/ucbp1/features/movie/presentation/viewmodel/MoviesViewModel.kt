package com.calyrsoft.ucbp1.features.movie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.movie.domain.model.Movie
import com.calyrsoft.ucbp1.features.movie.domain.usercase.GetPopularMoviesUseCase
import com.calyrsoft.ucbp1.features.movie.domain.usercase.GetSavedMoviesUseCase
import com.calyrsoft.ucbp1.features.movie.domain.usercase.SaveMovieUseCase
import com.calyrsoft.ucbp1.features.movie.domain.usercase.ToggleWatchLaterUseCase
import com.calyrsoft.ucbp1.features.movie.domain.usercase.UpdateMovieRatingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val saveMovieUseCase: SaveMovieUseCase,
    private val updateMovieRatingUseCase: UpdateMovieRatingUseCase,
    private val getSavedMoviesUseCase: GetSavedMoviesUseCase,
    private val toggleWatchLaterUseCase: ToggleWatchLaterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MoviesUiState>(MoviesUiState.Loading)
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = MoviesUiState.Loading
            val result = getPopularMoviesUseCase()
            result.fold(
                onSuccess = { apiMovies ->
                    // Obtener películas guardadas en BD
                    val savedMovies = try {
                        getSavedMoviesUseCase()
                    } catch (e: Exception) {
                        emptyList()
                    }
                    
                    // Crear mapas de datos guardados
                    val savedRatings = savedMovies.associate { it.id to it.rating }
                    val savedWatchLater = savedMovies.associate { it.id to it.watchLater }
                    
                    // Combinar datos de API con datos guardados
                    val moviesWithSavedData = apiMovies.map { movie ->
                        movie.copy(
                            rating = savedRatings[movie.id] ?: 0f,
                            watchLater = savedWatchLater[movie.id] ?: false
                        )
                    }
                    
                    // Guardar películas en BD
                    moviesWithSavedData.forEach { movie ->
                        saveMovieUseCase(movie)
                    }
                    
                    _uiState.value = MoviesUiState.Success(
                        moviesWithSavedData.sortedByDescending { it.rating }
                    )
                },
                onFailure = { exception ->
                    _uiState.value = MoviesUiState.Error(exception.message ?: "Error desconocido")
                }
            )
        }
    }

    fun updateMovieRating(movieId: Int, newRating: Float) {
        viewModelScope.launch {
            // Actualizar en BD
            updateMovieRatingUseCase(movieId, newRating)
            
            // Actualizar UI INMEDIATAMENTE
            val currentState = _uiState.value
            if (currentState is MoviesUiState.Success) {
                val updatedMovies = currentState.movies.map { movie ->
                    if (movie.id == movieId) {
                        movie.copy(rating = newRating)
                    } else {
                        movie
                    }
                }.sortedByDescending { it.rating }
                
                _uiState.value = MoviesUiState.Success(updatedMovies)
            }
        }
    }

    fun toggleWatchLater(movieId: Int) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is MoviesUiState.Success) {
                val movie = currentState.movies.find { it.id == movieId }
                val newWatchLaterState = !(movie?.watchLater ?: false)
                
                // Actualizar en BD
                toggleWatchLaterUseCase(movieId, newWatchLaterState)
                
                // Actualizar UI
                val updatedMovies = currentState.movies.map { m ->
                    if (m.id == movieId) {
                        m.copy(watchLater = newWatchLaterState)
                    } else {
                        m
                    }
                }
                
                _uiState.value = MoviesUiState.Success(updatedMovies)
            }
        }
    }

    sealed interface MoviesUiState {
        data object Loading : MoviesUiState
        data class Success(val movies: List<Movie>) : MoviesUiState
        data class Error(val message: String) : MoviesUiState
    }
}
