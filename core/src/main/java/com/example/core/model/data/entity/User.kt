package com.example.core.model.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "undefined",
    val lastName: String = "undefined",
    val email: String = "undefined",
    val hashedPassword: String = "undefined",
    val nationality: String = "undefined",
    var imageUrl: String = "undefined"
)
