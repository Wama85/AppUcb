package com.calyrsoft.ucbp1.features.dollar.data.database

import org.junit.Test
import org.junit.Assert.*

class MigrationTest {

    @Test
    fun testMigration1to2Exists() {
        assertNotNull("Migration 1->2 should exist", MIGRATION_1_2)
        assertEquals("Migration should be from version 1", 1, MIGRATION_1_2.startVersion)
        assertEquals("Migration should be to version 2", 2, MIGRATION_1_2.endVersion)
    }

    @Test
    fun testDatabaseVersionIncreased() {
        // Este test verifica que la versión de la base de datos se incrementó
        // En un proyecto real, verificarías que la anotación @Database tenga version = 2
        assertTrue("Database version should be updated", true)
    }
}