package com.calyrsoft.ucbp1.features.dollar.domain.repository

import com.calyrsoft.ucbp1.features.dollar.domain.model.Dollar
import kotlinx.coroutines.flow.Flow

interface DollarRepository {
    fun getDollarValue(): Flow<Result<Dollar>>
}