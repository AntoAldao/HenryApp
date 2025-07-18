package com.example.core.repository

import com.example.core.model.data.dao.CartItemDao
import com.example.core.model.data.entity.CartItem
import com.example.core.model.data.remote.ApiService
import com.example.core.model.repository.CartRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class CartRepositoryTest {

    private lateinit var cartItemDao: CartItemDao
    private lateinit var apiService: ApiService
    private lateinit var cartRepository: CartRepository

    @Before
    fun setup() {
        cartItemDao = mock(CartItemDao::class.java)
        apiService = mock(ApiService::class.java)
        cartRepository = CartRepository(cartItemDao, apiService)
    }

    @Test
    fun `addCartItem calls insertCartItem on dao`() = runTest {
        val item = CartItem(
            id = 1,
            name = "Test Item",
            price = 10.0,
            imageUrl = "http://example.com/image.jpg",
            quantity = 2,
            hasDrink = true,
            description = "Test Description",
        )
        cartRepository.addCartItem(item)
        advanceUntilIdle()
        verify(cartItemDao).insertCartItem(item)
    }

    @Test
    fun `updateCartItem calls updateCartItem on dao`() = runTest {
        val item = CartItem(
            id = 1,
            name = "Test Item",
            price = 10.0,
            imageUrl = "http://example.com/image.jpg",
            quantity = 3,
            hasDrink = true,
            description = "Test Description",
        )
        cartRepository.updateCartItem(item)
        advanceUntilIdle()
        verify(cartItemDao).updateCartItem(item)
    }

    @Test
    fun `removeCartItem calls deleteCartItem on dao`() = runTest {
        val item = CartItem(
            id = 1,
            name = "Test Item",
            price = 10.0,
            imageUrl = "http://example.com/image.jpg",
            quantity = 1,
            hasDrink = true,
            description = "Test Description",
        )
        cartRepository.removeCartItem(item)
        advanceUntilIdle()
        verify(cartItemDao).deleteCartItem(item)
    }

    @Test
    fun `clearCart calls clearCart on dao`() = runTest {
        cartRepository.clearCart()
        advanceUntilIdle()
        verify(cartItemDao).clearCart()
    }

    @Test
    fun `getCartItems calls getOrderDetail on apiService`() = runTest {
        val orderId = "123"
        val email = "test@email.com"
        cartRepository.getCartItems(orderId, email)
        verify(apiService).getOrderDetail(email,orderId, )
    }
}