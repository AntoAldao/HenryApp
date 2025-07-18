package com.example.henryapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.core.model.data.entity.CartItem
import com.example.core.model.data.entity.Order
import com.example.core.model.data.entity.OrderResponse
import com.example.core.model.repository.CartRepository
import com.example.core.model.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository,
    private val itemRepository: CartRepository,
) : ViewModel() {

    suspend fun addOrder(order: Order): Order {
      try {
          return repository.addOrder(order)
      }catch (e: Exception) {

          throw RuntimeException("Error adding order: ${e.message}", e)
      }

    }

    suspend fun getOrdersByEmail(email: String): List<OrderResponse> {
        return repository.getOrdersByEmail(email)
    }

    suspend fun getCardItems(orderId: String, email: String): List<CartItem> {
        return itemRepository.getCartItems(orderId, email)
    }
}