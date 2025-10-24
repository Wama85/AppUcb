package com.calyrsoft.ucbp1.features.movie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.movie.domain.model.Movie
import com.calyrsoft.ucbp1.features.movie.domain.usercase.GetMovieByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieId: Int,
    private val getMovieByIdUseCase: GetMovieByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    init {
        loadMovieDetail()
    }

    private fun loadMovieDetail() {
        viewModelScope.launch {
            _uiState.value = MovieDetailUiState.Loading
            val result = getMovieByIdUseCase(movieId)
            result.fold(
                onSuccess = { movie ->
                    _uiState.value = MovieDetailUiState.Success(movie)
                },
                onFailure = { exception ->
                    _uiState.value = MovieDetailUiState.Error(
                        exception.message ?: "Error desconocido"
                    )
                }
            )
        }
    }

    sealed interface MovieDetailUiState {
        data object Loading : MovieDetailUiState
        data class Success(val movie: Movie) : MovieDetailUiState
        data class Error(val message: String) : MovieDetailUiState
    }
}