package com.example.henryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.data.entity.CartItem
import com.example.core.model.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {

    val cartItems: StateFlow<List<CartItem>> = repository.cartItems
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents: SharedFlow<String> = _errorEvents.asSharedFlow()

    fun addCartItem(cartItem: CartItem) = viewModelScope.launch {
        try {
            repository.addCartItem(cartItem)
        } catch (e: Exception) {
            _errorEvents.emit("Error al agregar item al carrito: ${e.message}")
        }
    }

    fun updateCartItem(cartItem: CartItem) = viewModelScope.launch {
        try {
            repository.updateCartItem(cartItem)
        } catch (e: Exception) {
            _errorEvents.emit("Error al actualizar item del carrito: ${e.message}")
        }
    }

    fun removeCartItem(cartItem: CartItem) = viewModelScope.launch {
        try {
            repository.removeCartItem(cartItem)
        } catch (e: Exception) {
            _errorEvents.emit("Error al eliminar item del carrito: ${e.message}")
        }
    }

    fun clearCart() = viewModelScope.launch {
        try {
            repository.clearCart()
        } catch (e: Exception) {
            _errorEvents.emit("Error al vaciar el carrito: ${e.message}")
        }
    }
}
