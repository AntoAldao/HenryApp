package com.example.henryapp.repository

import com.example.henryapp.model.data.dao.CartItemDao
import com.example.henryapp.model.data.entity.CartItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(private val cartItemDao: CartItemDao) {
    val cartItems: Flow<List<CartItem>> = cartItemDao.getAllCartItems()

    suspend fun addCartItem(cartItem: CartItem) = cartItemDao.insertCartItem(cartItem)

    suspend fun updateCartItem(cartItem: CartItem) = cartItemDao.updateCartItem(cartItem)

    suspend fun removeCartItem(cartItem: CartItem) = cartItemDao.deleteCartItem(cartItem)

    suspend fun getCartItems(orderId: Long) = cartItemDao.getCartItemsByOrderId(orderId)

    suspend fun clearCart() = cartItemDao.clearCart()
}
