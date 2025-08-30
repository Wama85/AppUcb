package com.calyrsoft.ucbp1.features.dollar.data

import com.calyrsoft.ucbp1.features.dollar.domain.model.Dollar
import com.calyrsoft.ucbp1.features.dollar.domain.repository.DollarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DollarRepositoryImpl : DollarRepository {
    override fun getDollarValue(): Flow<Result<Dollar>> = flow {
        try {
            // Simulamos obtener el valor del dólar
            val dollar = Dollar(value = 12.90f, lastUpdated = "2025-08-30")
            emit(Result.success(dollar))
        } catch (e: Exception) {
            emit(Result.failure(Exception("Error al obtener valor del dólar")))
        }
    }
}