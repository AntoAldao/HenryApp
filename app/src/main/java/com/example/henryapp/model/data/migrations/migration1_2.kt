package com.example.henryapp.model.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `Order` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `total` REAL NOT NULL,
                `date` INTEGER NOT NULL,
                `email` TEXT NOT NULL,
            )
            """.trimIndent()
        )
    }
}
