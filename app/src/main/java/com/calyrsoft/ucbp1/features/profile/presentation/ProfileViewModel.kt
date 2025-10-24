package com.calyrsoft.ucbp1.features.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.login.domain.usecase.GetUserSessionUseCase
import com.calyrsoft.ucbp1.features.login.domain.usecase.LogoutUseCase
import com.calyrsoft.ucbp1.features.profile.domain.model.Profile
import com.calyrsoft.ucbp1.features.profile.domain.usecase.GetProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val getUserSessionUseCase: GetUserSessionUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadUserSession()
        loadProfile()
    }

    // Recuperar sesión del usuario
    private fun loadUserSession() {
        viewModelScope.launch {
            getUserSessionUseCase().onSuccess { session ->
                _state.value = _state.value.copy(
                    userName = session.userName,
                    userId = session.userId
                )
            }.onFailure { e ->
                _state.value = _state.value.copy(
                    error = e.message
                )
            }
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            getProfileUseCase().collect { result ->
                result.onSuccess { profile ->
                    _state.value = _state.value.copy(
                        profileData = profile,
                        isLoading = false
                    )
                }.onFailure { e ->
                    _state.value = _state.value.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    //  Implementar logout
    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _state.value = _state.value.copy(isLoggedOut = true)
        }
    }
}

data class ProfileState(
    val userName: String = "",
    val userId: String = "",
    val profileData: Profile? = null,
    val error: String? = null,
    val isLoading: Boolean = true,
    val isLoggedOut: Boolean = false
)