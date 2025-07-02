package com.example.henryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.henryapp.model.data.entity.CartItem
import com.example.henryapp.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {
    val cartItems: StateFlow<List<CartItem>> = repository.cartItems
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addCartItem(cartItem: CartItem) = viewModelScope.launch {
        repository.addCartItem(cartItem)
    }

    fun updateCartItem(cartItem: CartItem) = viewModelScope.launch {
        repository.updateCartItem(cartItem)
    }

    fun removeCartItem(cartItem: CartItem) = viewModelScope.launch {
        repository.removeCartItem(cartItem)
    }


    fun clearCart() = viewModelScope.launch {
        repository.clearCart()
    }
}
