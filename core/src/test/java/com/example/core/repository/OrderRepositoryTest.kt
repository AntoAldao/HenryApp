package com.example.core.repository

import com.example.core.model.data.entity.CartItem
import com.example.core.model.data.entity.Order
import com.example.core.model.data.entity.OrderResponse
import com.example.core.model.data.remote.ApiService
import com.example.core.model.repository.OrderRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class OrderRepositoryTest {

    private lateinit var apiService: ApiService
    private lateinit var orderRepository: OrderRepository

    @Before
    fun setup() {
        apiService = mock(ApiService::class.java)
        orderRepository = OrderRepository(apiService)
    }

    @Test
    fun `addOrder calls api and returns id`() = runTest {
        val cartItems = listOf(
            CartItem(
                id = 1,
                name = "Producto 1",
                price = 10.0,
                imageUrl = "http://example.com/1.jpg",
                quantity = 2,
                hasDrink = false,
                description = "Desc 1"
            )
        )
        val order = Order(
            orderId = 1L,
            email = "test@example.com",
            total = 100.0,
            date = System.currentTimeMillis(),
            productIds = cartItems
        )

        `when`(apiService.addOrder(order)).thenReturn(order)

        val result = orderRepository.addOrder(order)

        advanceUntilIdle()
        assertEquals(order, result)
        verify(apiService).addOrder(order)
    }

    @Test
    fun `getOrdersByEmail calls api method`() = runTest {
        val email = "test@example.com"
        val cartItems = listOf(
            CartItem(
                id = 1,
                name = "Producto 1",
                price = 10.0,
                imageUrl = "http://example.com/1.jpg",
                quantity = 2,
                hasDrink = false,
                description = "Desc 1"
            )
        )
        val orders = listOf(

             OrderResponse(
                 orderId = 1L,
                 email = "test@example.com",
                 total = 100.0,
                 date = System.currentTimeMillis(),
                 productIds = cartItems,
                _id = "1234567890abcdef",
            )
        )

        `when`(apiService.getOrderHistory(email)).thenReturn(orders)
        advanceUntilIdle()
        val result = orderRepository.getOrdersByEmail(email)
        assertEquals(orders, result)
        verify(apiService).getOrderHistory(email)
    }
}