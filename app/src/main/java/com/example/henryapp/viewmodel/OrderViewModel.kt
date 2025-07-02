package com.example.henryapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.henryapp.model.data.entity.CartItem
import com.example.henryapp.model.data.entity.Order
import com.example.henryapp.repository.CartRepository
import com.example.henryapp.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository,
    private val itemRepository: CartRepository,
) : ViewModel() {

    suspend fun addOrder(total: Double, email: String): Long {
        val order = Order(
            total = total,
            date = System.currentTimeMillis(),
            email = email
        )
        return repository.addOrder(order)
    }

    suspend fun getOrdersByEmail(email: String): List<Order> {
        return repository.getOrdersByEmail(email).first()
    }

    suspend fun getCardItems(orderId: Long): List<CartItem> {
        return itemRepository.getCartItems(orderId).first()
    }
}