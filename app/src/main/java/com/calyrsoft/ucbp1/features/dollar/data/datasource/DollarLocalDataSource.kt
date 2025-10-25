package com.calyrsoft.ucbp1.features.dollar.data.datasource

import com.calyrsoft.ucbp1.features.dollar.data.database.dao.IDollarDao
import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import com.calyrsoft.ucbp1.features.dollar.data.mapper.toModel
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel

class DollarLocalDataSource(private val dollarDao: IDollarDao) {

    suspend fun insert(dollar: DollarEntity) {
        dollarDao.insert(dollar)
    }

    suspend fun getAllDollars(): List<DollarModel> {
        return dollarDao.getList().map { it.toModel() }
    }

    suspend fun getAllOrderedByDate(): List<DollarModel> {
        return dollarDao.getAllOrderedByDate().map { it.toModel() }
    }

    suspend fun deleteAll() {
        dollarDao.deleteAll()
    }

    suspend fun deleteById(id: Int) {
        dollarDao.deleteById(id)
    }
}
