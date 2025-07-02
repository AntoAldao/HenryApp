package com.example.henryapp.model.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val total: Double,
    val date: Long,
    val email: String
)
