package com.calyrsoft.ucbp1.features.dollar.domain.model

data class DollarModel(
    val dollarOfficialCompra: String? = null,
    val dollarOfficialVenta: String? = null,
    val dollarParallelCompra: String? = null,
    val dollarParallelVenta: String? = null,

    var timestamp: Long = 0L
)