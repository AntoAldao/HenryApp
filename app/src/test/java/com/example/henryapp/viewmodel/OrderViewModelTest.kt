package com.example.henryapp.viewmodel

import com.example.core.model.data.entity.CartItem
import com.example.core.model.data.entity.Order
import com.example.core.model.repository.CartRepository
import com.example.core.model.repository.OrderRepository
import com.example.henryapp.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class OrderViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var repository: OrderRepository
    private lateinit var viewModel: OrderViewModel
    private lateinit var cartRepository: CartRepository
    private lateinit var cartViewModel: CartViewModel

    private val mockOrderFlow = MutableStateFlow<List<Order>>(emptyList())

    @Before
    fun setup() {
        repository = mock(OrderRepository::class.java)
        cartRepository = mock(CartRepository::class.java)
        whenever(repository.orders).thenReturn(mockOrderFlow)
        whenever(repository.getOrdersByEmail(any())).thenReturn(mockOrderFlow)
        whenever(cartRepository.getCartItems(any())).thenReturn(MutableStateFlow(emptyList<CartItem>()))
        viewModel = OrderViewModel(repository, cartRepository)
        cartViewModel = CartViewModel(cartRepository)
    }

    @Test
    fun `addOrder should call repository`() = runTest {
        val total = 150.00
        val email = "emailTest"
        val order = Order(
            total = total,
            date = System.currentTimeMillis(),
            email = email
        )

        viewModel.addOrder(total, email)
        advanceUntilIdle()

        verify(repository).addOrder(order)
    }

    @Test
    fun `getOrders by email`() = runTest {

        val total = 150.00
        val email = "emailTest"

        viewModel.addOrder(total, email)
        advanceUntilIdle()

        viewModel.getOrdersByEmail(email)
        advanceUntilIdle()

        verify(repository).getOrdersByEmail(email)
    }

    @Test
    fun `getCardItems should call repository`() = runTest {
        val orderId = 1L

        val item = CartItem(id = 1, name = "Test Item", price = 10.0, imageUrl = "url", quantity = 1, orderId = orderId)

        cartViewModel.addCartItem(item)

        viewModel.getCardItems(orderId)
        advanceUntilIdle()

        verify(cartRepository).getCartItems(orderId)
    }
}
