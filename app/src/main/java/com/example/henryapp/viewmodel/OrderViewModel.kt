package com.example.henryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.data.entity.CartItem
import com.example.core.model.data.entity.Order
import com.example.core.model.data.entity.OrderResponse
import com.example.core.model.repository.CartRepository
import com.example.core.model.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository,
    private val itemRepository: CartRepository,
) : ViewModel() {

    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents = _errorEvents.asSharedFlow()

    suspend fun addOrder(order: Order): Order {
        return try {
            repository.addOrder(order)
        } catch (e: Exception) {
            viewModelScope.launch {
                _errorEvents.emit("Error al agregar la orden: ${e.message}")
            }
            throw e
        }
    }

    suspend fun getOrdersByEmail(email: String): List<OrderResponse> {
        return try {
            repository.getOrdersByEmail(email)
        } catch (e: Exception) {
            viewModelScope.launch {
                _errorEvents.emit("Error al obtener las órdenes: ${e.message}")
            }
            emptyList()
        }
    }

    suspend fun getCardItems(orderId: String, email: String): List<CartItem> {
        return try {
            itemRepository.getCartItems(orderId, email)
        } catch (e: Exception) {
            viewModelScope.launch {
                _errorEvents.emit("Error al obtener los ítems del carrito: ${e.message}")
            }
            emptyList()
        }
    }
}
