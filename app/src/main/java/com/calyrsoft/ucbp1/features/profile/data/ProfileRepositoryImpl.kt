package com.calyrsoft.ucbp1.features.profile.data

import com.calyrsoft.ucbp1.features.profile.domain.model.Profile
import com.calyrsoft.ucbp1.features.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileRepositoryImpl : ProfileRepository {
    override fun getProfile(): Flow<Result<Profile>> = flow {
        // Simulamos datos de perfil por ahora
        val profile = Profile(
            id = "user_123",
            name = "Usuario Demo",
            email = "usuario@ejemplo.com"
        )
        emit(Result.success(profile))
    }
}