package com.example.core.model.repository

import com.example.core.model.data.entity.Order
import com.example.core.model.data.entity.OrderResponse
import com.example.core.model.data.remote.ApiService
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun addOrder(order: Order): Order {
        return apiService.addOrder(order)
    }

    suspend fun getOrdersByEmail(email: String): List<OrderResponse> {
        return apiService.getOrderHistory(email)
    }

}
