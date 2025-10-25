package com.calyrsoft.ucbp1.features.login.data

import com.calyrsoft.ucbp1.features.login.data.datasource.LoginDataStore
import com.calyrsoft.ucbp1.features.login.domain.model.LoginRequest
import com.calyrsoft.ucbp1.features.login.domain.model.LoginResponse
import com.calyrsoft.ucbp1.features.login.domain.repository.LoginRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(
    private val loginDataStore: LoginDataStore
) : LoginRepository {

    override fun login(request: LoginRequest): Flow<Result<LoginResponse>> = flow {
        try {
            delay(1000) // simulamos llamada de red

            if (request.username.isEmpty() || request.password.isEmpty()) {
                emit(Result.failure(Exception("⚠️ Usuario y contraseña son requeridos")))
                return@flow
            }

            println("MOCK LOGIN → usuario=${request.username}, tokenFCM=${request.fcmToken}")

            // 🔹 Simulación de login exitoso
            if ((request.username == "admin" && request.password == "admin") ||
                (request.username == "123" && request.password == "123")
            ) {
                val response = LoginResponse(
                    token = "mock_token_${System.currentTimeMillis()}",
                    userId = "user_${request.username}",
                    message = "✅ Bienvenido ${request.username}"
                )
                emit(Result.success(response))
            } else {
                emit(Result.failure(Exception("❌ Credenciales inválidas")))
            }
        } catch (e: Exception) {
            emit(Result.failure(Exception("Error simulado: ${e.message ?: "desconocido"}")))
        }
    }

    // 🔹 Métodos de sesión (mock + DataStore)
    override suspend fun saveSession(userName: String, token: String, userId: String) {
        loginDataStore.saveSession(userName, token, userId)
    }

    override suspend fun getUserName(): Result<String> = loginDataStore.getUserName()
    override suspend fun getToken(): Result<String> = loginDataStore.getToken()
    override suspend fun getUserId(): Result<String> = loginDataStore.getUserId()
    override suspend fun isLoggedIn(): Boolean = loginDataStore.isLoggedIn()
    override suspend fun clearSession() = loginDataStore.clearSession()
}
