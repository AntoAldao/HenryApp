package com.example.henryapp.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {
    private val selectedQuantities = mutableStateMapOf<String, Int>()

    fun getQuantity(productId: String): Int {
        return selectedQuantities[productId] ?: 1
    }

    fun increaseQuantity(productId: String) {
        val currentQuantity = selectedQuantities[productId] ?: 1
        selectedQuantities[productId] = currentQuantity + 1
    }

    fun decreaseQuantity(productId: String) {
        val currentQuantity = selectedQuantities[productId] ?: 1
        if (currentQuantity > 1) {
            selectedQuantities[productId] = currentQuantity - 1
        }
    }
}
