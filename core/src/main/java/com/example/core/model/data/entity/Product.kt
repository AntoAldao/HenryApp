package com.example.core.model.data.entity

data class Product(
    val _id: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val price: Double,
    val hasDrink: Boolean
)
