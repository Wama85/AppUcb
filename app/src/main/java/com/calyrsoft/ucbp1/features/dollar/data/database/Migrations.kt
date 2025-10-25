package com.calyrsoft.ucbp1.features.dollar.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE dollars ADD COLUMN source TEXT DEFAULT 'unknown' NOT NULL")
    }
}