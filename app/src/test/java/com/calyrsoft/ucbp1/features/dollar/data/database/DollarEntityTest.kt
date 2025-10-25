package com.calyrsoft.ucbp1.features.dollar.data.database

import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import org.junit.Test
import org.junit.Assert.*

class DollarEntityTest {

    @Test
    fun testDollarEntityCreation() {
        val dollar = DollarEntity(
            id = 1,
            dollarOfficial = "6.96",
            dollarParallel = "7.50",
            timestamp = 1234567890,
            source = "API"
        )

        assertEquals(1, dollar.id)
        assertEquals("6.96", dollar.dollarOfficial)
        assertEquals("7.50", dollar.dollarParallel)
        assertEquals(1234567890L, dollar.timestamp)
        assertEquals("API", dollar.source)
    }

    @Test
    fun testDollarEntityDefaultValues() {
        val dollar = DollarEntity()

        assertEquals(0, dollar.id)
        assertNull(dollar.dollarOfficial)
        assertNull(dollar.dollarParallel)
        assertEquals(0L, dollar.timestamp)
        assertEquals("unknown", dollar.source)
    }

    @Test
    fun testDollarEntityWithPartialData() {
        val dollar = DollarEntity(
            dollarOfficial = "6.96",
            dollarParallel = "7.50",
            timestamp = System.currentTimeMillis()
        )

        assertEquals("unknown", dollar.source)
        assertEquals(0, dollar.id)
    }

    @Test
    fun testSourceFieldExists() {
        val dollar = DollarEntity()
        assertNotNull("Source field should exist", dollar.source)
        assertEquals("Default source should be 'unknown'", "unknown", dollar.source)
    }
}