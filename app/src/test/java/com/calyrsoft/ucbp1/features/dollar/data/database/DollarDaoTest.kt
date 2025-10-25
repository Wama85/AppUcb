package com.calyrsoft.ucbp1.features.dollar.data.database

import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import org.junit.Test
import org.junit.Assert.*

class DollarDaoTest {

    @Test
    fun testDollarEntityForDaoOperations() {
        // Test que verifica que DollarEntity tiene todos los campos necesarios
        val dollar = DollarEntity(
            id = 1,
            dollarOfficial = "6.96",
            dollarParallel = "7.50",
            timestamp = System.currentTimeMillis(),
            source = "API"
        )

        assertEquals(1, dollar.id)
        assertEquals("6.96", dollar.dollarOfficial)
        assertEquals("7.50", dollar.dollarParallel)
        assertTrue(dollar.timestamp > 0)
        assertEquals("API", dollar.source)
    }

    @Test
    fun testMultipleDollarEntities() {
        val dollars = listOf(
            DollarEntity(dollarOfficial = "6.96", dollarParallel = "7.50", timestamp = 1000),
            DollarEntity(dollarOfficial = "7.00", dollarParallel = "7.60", timestamp = 2000),
            DollarEntity(dollarOfficial = "7.05", dollarParallel = "7.70", timestamp = 3000)
        )

        assertEquals(3, dollars.size)
        assertEquals("6.96", dollars[0].dollarOfficial)
        assertEquals("7.60", dollars[1].dollarParallel)
        assertEquals(3000L, dollars[2].timestamp)
    }

    @Test
    fun testDollarEntityDefaultSource() {
        val dollar = DollarEntity(
            dollarOfficial = "6.96",
            dollarParallel = "7.50",
            timestamp = 1000
        )

        assertEquals("unknown", dollar.source)
    }

    @Test
    fun testDollarEntityNullableFields() {
        val dollar = DollarEntity()

        assertNull(dollar.dollarOfficial)
        assertNull(dollar.dollarParallel)
        assertEquals(0L, dollar.timestamp)
        assertEquals("unknown", dollar.source)
    }
}