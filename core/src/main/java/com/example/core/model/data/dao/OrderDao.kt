package com.example.core.model.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.core.model.data.entity.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM `Order` ORDER BY date DESC")
    fun getAllOrders(): Flow<List<Order>>

    @Query("SELECT * FROM `Order` WHERE email = :email ORDER BY date DESC")
    fun getOrdersByEmail(email:String): Flow<List<Order>>

    @Insert
    suspend fun insertOrder(order: Order): Long
}