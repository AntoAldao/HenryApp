package com.example.core.model.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.core.model.data.entity.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao  {
//    @Query("SELECT * FROM cart_items WHERE orderId = :orderId ORDER BY id ASC")
//    fun getCartItemsByOrderId(orderId:Long): Flow<List<CartItem>>
//
    @Query("SELECT * FROM cart_items ORDER BY id ASC ")
    fun getAllCartItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}
