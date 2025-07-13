package com.example.core.model.repository

import com.example.core.model.data.dao.OrderDao
import com.example.core.model.data.entity.Order
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderDao: OrderDao,
) {
    fun orders(): Flow<List<Order>> = orderDao.getAllOrders()

    suspend fun addOrder(order: Order): Long {
        return orderDao.insertOrder(order)

    }

    fun getOrdersByEmail(email: String): Flow<List<Order>> {
        return orderDao.getOrdersByEmail(email)
    }

}
