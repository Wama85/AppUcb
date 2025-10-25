package com.calyrsoft.ucbp1.features.dollar.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE dollars ADD COLUMN source TEXT DEFAULT 'unknown' NOT NULL")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Crear tabla temporal con la nueva estructura
        db.execSQL("""
            CREATE TABLE dollars_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                dollar_official_compra TEXT,
                dollar_official_venta TEXT,
                dollar_parallel_compra TEXT,
                dollar_parallel_venta TEXT,
                timestamp INTEGER NOT NULL,
                source TEXT NOT NULL DEFAULT 'unknown'
            )
        """)
        
        // Copiar datos existentes (los valores antiguos van a "compra")
        db.execSQL("""
            INSERT INTO dollars_new (id, dollar_official_compra, dollar_parallel_compra, timestamp, source)
            SELECT id, dollar_official, dollar_parallel, timestamp, source
            FROM dollars
        """)
        
        // Eliminar tabla vieja
        db.execSQL("DROP TABLE dollars")
        
        // Renombrar tabla nueva
        db.execSQL("ALTER TABLE dollars_new RENAME TO dollars")
    }
}
