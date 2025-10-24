package com.calyrsoft.ucbp1.features.login.domain.repository

import com.calyrsoft.ucbp1.features.login.domain.model.LoginRequest
import com.calyrsoft.ucbp1.features.login.domain.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(request: LoginRequest): Flow<Result<LoginResponse>>

    // Nuevos métodos para gestión de sesión
    suspend fun saveSession(userName: String, token: String, userId: String)
    suspend fun getUserName(): Result<String>
    suspend fun getToken(): Result<String>
    suspend fun getUserId(): Result<String>
    suspend fun isLoggedIn(): Boolean
    suspend fun clearSession()
}