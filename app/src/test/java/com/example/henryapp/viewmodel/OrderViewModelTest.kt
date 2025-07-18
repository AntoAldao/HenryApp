package com.example.henryapp.viewmodel

import com.example.core.model.data.entity.CartItem
import com.example.core.model.data.entity.Order
import com.example.core.model.data.entity.OrderResponse
import com.example.core.model.repository.CartRepository
import com.example.core.model.repository.OrderRepository
import com.example.henryapp.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class OrderViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var repository: OrderRepository
    private lateinit var viewModel: OrderViewModel
    private lateinit var cartRepository: CartRepository

    @Before
    fun setup() {
        repository = mock(OrderRepository::class.java)
        cartRepository = mock(CartRepository::class.java)
        viewModel = OrderViewModel(repository, cartRepository)
    }

    @Test
    fun `addOrder should call repository`() = runTest {
        val total = 150.00
        val email = "emailTest"
        val cartItems = listOf<CartItem>()
        val order = Order(
            total = total,
            date = System.currentTimeMillis(),
            email = email,
            productIds = cartItems
        )
        whenever(repository.addOrder(order)).thenReturn(order)

        viewModel.addOrder(order)
        advanceUntilIdle()

        verify(repository).addOrder(order)
    }

    @Test
    fun `getOrdersByEmail should call repository`() = runTest {
        val email = "emailTest"
        val response = listOf<OrderResponse>()
        whenever(repository.getOrdersByEmail(email)).thenReturn(response)

        viewModel.getOrdersByEmail(email)
        advanceUntilIdle()

        verify(repository).getOrdersByEmail(email)
    }

    @Test
    fun `getCardItems should call cartRepository`() = runTest {
        val orderId = "1"
        val email = "emailTest"
        val cartItems = listOf<CartItem>()
        whenever(cartRepository.getCartItems(orderId, email)).thenReturn(cartItems)

        viewModel.getCardItems(orderId, email)
        advanceUntilIdle()

        verify(cartRepository).getCartItems(orderId, email)
    }
}