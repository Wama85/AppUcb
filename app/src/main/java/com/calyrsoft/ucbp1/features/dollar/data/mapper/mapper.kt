package com.calyrsoft.ucbp1.features.dollar.data.mapper

import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel

fun DollarEntity.toModel() : DollarModel {
    return DollarModel(
        dollarOfficialCompra = dollarOfficialCompra,
        dollarOfficialVenta =dollarOfficialVenta,
        dollarParallelCompra = dollarParallelCompra,
        dollarParallelVenta=dollarParallelVenta,
        timestamp = timestamp
    )
}

fun DollarModel.toEntity() : DollarEntity {
    return DollarEntity(
        dollarOfficialCompra = dollarOfficialCompra,
        dollarOfficialVenta =dollarOfficialVenta,
        dollarParallelCompra = dollarParallelCompra,
        dollarParallelVenta=dollarParallelVenta,
        timestamp = if (timestamp == 0L) System.currentTimeMillis() else timestamp
    )
}
