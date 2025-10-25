package com.calyrsoft.ucbp1.features.dollar.data.repository

import com.calyrsoft.ucbp1.features.dollar.data.datasource.RealTimeRemoteDataSource
import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarLocalDataSource
import com.calyrsoft.ucbp1.features.dollar.data.mapper.toEntity
import com.calyrsoft.ucbp1.features.dollar.data.mapper.toModel
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.domain.repository.IDollarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class DollarRepositoryImpl(
    private val realTimeRemoteDataSource: RealTimeRemoteDataSource,
    private val localDataSource: DollarLocalDataSource
) : IDollarRepository {

    /**
     * Obtiene actualizaciones del dólar en tiempo real desde Firebase
     * y las guarda en Room como historial.
     */
    override fun getDollar(): Flow<DollarModel> {
        return realTimeRemoteDataSource.getDollarUpdates()
            .onEach { dollar ->
                try {
                    // 🔹 Guarda cada actualización en la base local
                    localDataSource.insert(dollar.toEntity())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
    }

    /**
     * Obtiene el historial guardado localmente en Room,
     * ordenado por fecha descendente.
     */
    override fun getDollarHistory(): Flow<List<DollarModel>> = flow {
        try {
            val localList = localDataSource.getAllOrderedByDate()
            emit(localList)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }.catch { emit(emptyList()) }
}
