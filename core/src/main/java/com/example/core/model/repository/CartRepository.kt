package com.example.core.model.repository

import com.example.core.model.data.dao.CartItemDao
import com.example.core.model.data.entity.CartItem
import com.example.core.model.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartItemDao: CartItemDao,
    private val apiService: ApiService
) {
    val cartItems: Flow<List<CartItem>> = cartItemDao.getAllCartItems()

    suspend fun addCartItem(cartItem: CartItem) = cartItemDao.insertCartItem(cartItem)

    suspend fun updateCartItem(cartItem: CartItem) = cartItemDao.updateCartItem(cartItem)

    suspend fun removeCartItem(cartItem: CartItem) = cartItemDao.deleteCartItem(cartItem)

    suspend fun getCartItems(orderId: String, email: String): List<CartItem> {
        return apiService.getOrderDetail(email,orderId)
    }

    suspend fun clearCart() = cartItemDao.clearCart()
}
