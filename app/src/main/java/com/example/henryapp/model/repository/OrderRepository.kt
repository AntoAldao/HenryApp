package com.example.henryapp.repository

import com.example.henryapp.model.data.dao.OrderDao
import com.example.henryapp.model.data.entity.Order
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderDao: OrderDao,
) {
    val orders: Flow<List<Order>> = orderDao.getAllOrders()

    suspend fun addOrder(order: Order): Long {
        return orderDao.insertOrder(order)

    }

    suspend fun getOrdersByEmail(email: String): Flow<List<Order>> {
        return orderDao.getOrdersByEmail(email)
    }

}
