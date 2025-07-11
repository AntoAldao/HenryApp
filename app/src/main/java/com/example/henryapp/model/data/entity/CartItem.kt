package com.example.henryapp.model.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val price: Double,
    val imageUrl: String,
    var quantity: Int,
    var orderId: Long,
    //val category: String
)
