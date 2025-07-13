package com.example.core.repository

import com.example.core.model.data.dao.OrderDao
import com.example.core.model.data.entity.Order
import com.example.core.model.repository.OrderRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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

    private lateinit var orderDao: OrderDao
    private lateinit var orderRepository: OrderRepository

    @Before
    fun setup() {
        orderDao = mock(OrderDao::class.java)
        orderRepository = OrderRepository(orderDao)
    }

    @Test
    fun `addOrder calls insertOrder and returns id`() = runTest {
        val order = Order(
            id = 0L,
            email = "test@example.com",
            total = 100.0,
            date = System.currentTimeMillis()
        )
        `when`(orderDao.insertOrder(order)).thenReturn(1L)

        val result = orderRepository.addOrder(order)

        advanceUntilIdle()
        assertEquals(1L, result)
        verify(orderDao).insertOrder(order)
    }

    @Test
    fun `getOrdersByEmail calls DAO method`() = runTest {
        val email = "test@example.com"
        val orders = listOf(
            Order(id = 1L, email = email, total = 50.0, date = 123456789L),
            Order(id = 2L, email = email, total =  75.0, date = 123456799L)
        )
        val flow = flowOf(orders)

        `when`(orderDao.getOrdersByEmail(email)).thenReturn(flow)
        advanceUntilIdle()
        val resultFlow = orderRepository.getOrdersByEmail(email)
        resultFlow.collect { result ->
            assertEquals(orders, result)
        }
    }

    @Test
    fun `orders exposes all orders from DAO`() = runTest {
        val allOrders = listOf(
            Order(id = 1L, email = "a@example.com", total = 100.0, date = 1111L),
            Order(id = 2L, email = "b@example.com", total = 200.0, date = 2222L)
        )
        val flow = flowOf(allOrders)

        `when`(orderDao.getAllOrders()).thenReturn(flow)

        orderRepository.orders().collect { result ->
            assertEquals(allOrders, result)
        }
    }
}
