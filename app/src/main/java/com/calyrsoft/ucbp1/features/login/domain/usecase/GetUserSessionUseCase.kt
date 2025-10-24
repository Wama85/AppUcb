package com.calyrsoft.ucbp1.features.login.domain.usecase

import com.calyrsoft.ucbp1.features.login.domain.repository.LoginRepository

data class UserSession(
    val userName: String,
    val token: String,
    val userId: String
)

class GetUserSessionUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): Result<UserSession> {
        return try {
            val userName = loginRepository.getUserName().getOrThrow()
            val token = loginRepository.getToken().getOrThrow()
            val userId = loginRepository.getUserId().getOrThrow()

            Result.success(UserSession(userName, token, userId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}