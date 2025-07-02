package com.example.henryapp.model.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // 1. Crear la tabla nueva con esquema esperado y DEFAULT 'undefined' en cada columna NOT NULL
        database.execSQL("""
            CREATE TABLE users_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL DEFAULT 'undefined',
                name TEXT NOT NULL DEFAULT 'undefined',
                lastName TEXT NOT NULL DEFAULT 'undefined',
                email TEXT NOT NULL DEFAULT 'undefined',
                hashedPassword TEXT NOT NULL DEFAULT 'undefined',
                nationality TEXT NOT NULL DEFAULT 'undefined',
                imageUrl TEXT NOT NULL DEFAULT 'undefined'
            )
        """)

        // 2. Copiar los datos antiguos a la nueva tabla, para imageUrl poner 'undefined'
        database.execSQL("""
            INSERT INTO users_new (id, name, lastName, email, hashedPassword, nationality, imageUrl)
            SELECT id, name, lastName, email, hashedPassword, nationality, 'undefined' FROM users
        """)

        // 3. Borrar tabla antigua
        database.execSQL("DROP TABLE users")

        // 4. Renombrar la nueva tabla a la original
        database.execSQL("ALTER TABLE users_new RENAME TO users")
    }
}
