package com.example.henryapp.model.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `users` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `name` TEXT NOT NULL,
                `lastName` TEXT NOT NULL,
                `email` TEXT NOT NULL,
                `hashedPassword` TEXT NOT NULL,
                `nationality` TEXT NOT NULL
            )
            """.trimIndent()
        )
    }
}
