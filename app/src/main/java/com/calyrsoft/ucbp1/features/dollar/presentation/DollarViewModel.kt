package com.calyrsoft.ucbp1.features.dollar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.GetDollarUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DollarViewModel(
    private val getDollarUseCase: GetDollarUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DollarState>(DollarState.Loading)
    val state: StateFlow<DollarState> = _state.asStateFlow()

    fun loadDollarValue() {
        viewModelScope.launch {
            _state.value = DollarState.Loading

            getDollarUseCase().collect { result ->
                result.onSuccess { dollar ->
                    _state.value = DollarState.Loaded(
                        value = dollar.value,
                        lastUpdated = dollar.lastUpdated
                    )
                }.onFailure { e ->
                    _state.value = DollarState.Error(
                        message = e.message ?: "Error al cargar el tipo de cambio"
                    )
                }
            }
        }
    }
}

sealed class DollarState {
    object Loading : DollarState()
    data class Loaded(val value: Float, val lastUpdated: String) : DollarState()
    data class Error(val message: String) : DollarState()
}