package com.example.core.model.data.entity

data class OrderResponse(
    val orderId: Long,
    val total: Double,
    val date: Long,
    val email: String,
    val productIds : List<CartItem>,
    val _id: String
)