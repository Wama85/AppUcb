package com.calyrsoft.ucbp1.features.dollar.domain.usecase

import com.calyrsoft.ucbp1.features.dollar.domain.model.Dollar
import com.calyrsoft.ucbp1.features.dollar.domain.repository.DollarRepository
import kotlinx.coroutines.flow.Flow

class GetDollarUseCase(
    private val dollarRepository: DollarRepository
) {
    operator fun invoke(): Flow<Result<Dollar>> {
        return dollarRepository.getDollarValue()
    }
}