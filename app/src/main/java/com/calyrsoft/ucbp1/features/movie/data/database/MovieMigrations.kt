package com.calyrsoft.ucbp1.features.movie.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MOVIE_MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE movies ADD COLUMN watch_later INTEGER NOT NULL DEFAULT 0")
    }
}
