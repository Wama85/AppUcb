package com.calyrsoft.ucbp1.features.login.domain.usecase

import com.calyrsoft.ucbp1.features.login.domain.model.LoginRequest
import com.calyrsoft.ucbp1.features.login.domain.model.LoginResponse
import com.calyrsoft.ucbp1.features.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(username: String, password: String): Flow<Result<LoginResponse>> {
        val request = LoginRequest(username, password)
        return loginRepository.login(request)
    }
}