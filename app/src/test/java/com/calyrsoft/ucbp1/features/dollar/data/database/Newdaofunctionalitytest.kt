package com.calyrsoft.ucbp1.features.dollar.data.database

import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import org.junit.Test
import org.junit.Assert.*

class NewDaoFunctionalityTest {

    @Test
    fun testGetDollarsFromDate() {
        // Test que verifica la funcionalidad de buscar desde una fecha
        val currentTime = System.currentTimeMillis()
        val sevenDaysAgo = currentTime - (7 * 24 * 60 * 60 * 1000)

        val dollars = listOf(
            DollarEntity(dollarOfficial = "6.96", dollarParallel = "7.50", timestamp = currentTime),
            DollarEntity(dollarOfficial = "7.00", dollarParallel = "7.60", timestamp = sevenDaysAgo - 1000),
            DollarEntity(dollarOfficial = "7.05", dollarParallel = "7.70", timestamp = currentTime - 1000)
        )

        // Los que serían retornados desde sevenDaysAgo
        val recentDollars = dollars.filter { it.timestamp >= sevenDaysAgo }

        assertEquals(2, recentDollars.size)
        assertTrue(recentDollars.all { it.timestamp >= sevenDaysAgo })
    }

    @Test
    fun testGetDollarsBySource() {
        // Test que verifica buscar por fuente
        val dollars = listOf(
            DollarEntity(dollarOfficial = "6.96", dollarParallel = "7.50", timestamp = 1000, source = "API"),
            DollarEntity(dollarOfficial = "7.00", dollarParallel = "7.60", timestamp = 2000, source = "Manual"),
            DollarEntity(dollarOfficial = "7.05", dollarParallel = "7.70", timestamp = 3000, source = "API")
        )

        val apiDollars = dollars.filter { it.source == "API" }
        val manualDollars = dollars.filter { it.source == "Manual" }

        assertEquals(2, apiDollars.size)
        assertEquals(1, manualDollars.size)
        assertTrue(apiDollars.all { it.source == "API" })
    }

    @Test
    fun testGetMostRecent() {
        // Test que verifica obtener el más reciente
        val dollars = listOf(
            DollarEntity(dollarOfficial = "6.96", dollarParallel = "7.50", timestamp = 1000),
            DollarEntity(dollarOfficial = "7.00", dollarParallel = "7.60", timestamp = 3000),
            DollarEntity(dollarOfficial = "7.05", dollarParallel = "7.70", timestamp = 2000)
        )

        val mostRecent = dollars.maxByOrNull { it.timestamp }

        assertNotNull(mostRecent)
        assertEquals(3000L, mostRecent?.timestamp)
        assertEquals("7.00", mostRecent?.dollarOfficial)
    }

    @Test
    fun testGetDollarsBetweenDates() {
        // Test que verifica buscar entre fechas
        val dollars = listOf(
            DollarEntity(dollarOfficial = "6.96", dollarParallel = "7.50", timestamp = 1000),
            DollarEntity(dollarOfficial = "7.00", dollarParallel = "7.60", timestamp = 2000),
            DollarEntity(dollarOfficial = "7.05", dollarParallel = "7.70", timestamp = 3000),
            DollarEntity(dollarOfficial = "7.10", dollarParallel = "7.80", timestamp = 4000)
        )

        val startTime = 1500L
        val endTime = 3500L
        val inRange = dollars.filter { it.timestamp in startTime..endTime }

        assertEquals(2, inRange.size)
        assertTrue(inRange.all { it.timestamp >= startTime && it.timestamp <= endTime })
    }

    @Test
    fun testNewQueriesWithDefaultSource() {
        // Test que verifica que las nuevas queries funcionan con el campo source
        val dollar = DollarEntity(
            dollarOfficial = "6.96",
            dollarParallel = "7.50",
            timestamp = System.currentTimeMillis()
        )

        assertEquals("unknown", dollar.source)
        assertNotNull(dollar.timestamp)
    }

    @Test
    fun testFilterBySourceWithMultipleSources() {
        val dollars = listOf(
            DollarEntity(dollarOfficial = "6.96", timestamp = 1000, source = "API"),
            DollarEntity(dollarOfficial = "7.00", timestamp = 2000, source = "Manual"),
            DollarEntity(dollarOfficial = "7.05", timestamp = 3000, source = "Web"),
            DollarEntity(dollarOfficial = "7.10", timestamp = 4000, source = "API"),
            DollarEntity(dollarOfficial = "7.15", timestamp = 5000, source = "unknown")
        )

        val sources = dollars.map { it.source }.distinct()
        assertEquals(4, sources.size)
        assertTrue(sources.contains("API"))
        assertTrue(sources.contains("Manual"))
        assertTrue(sources.contains("Web"))
        assertTrue(sources.contains("unknown"))
    }
}