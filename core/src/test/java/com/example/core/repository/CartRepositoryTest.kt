package com.example.core.repository

import com.example.core.model.data.dao.CartItemDao
import com.example.core.model.data.entity.CartItem
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
    private lateinit var cartRepository: CartRepository

    @Before
    fun setup() {
        cartItemDao = mock(CartItemDao::class.java)
        cartRepository = CartRepository(cartItemDao)
    }

    @Test
    fun `addCartItem calls insertCartItem on dao`() = runTest {
        val item = CartItem(
            id = 1,
            name = "Test Item",
            price = 10.0,
            imageUrl = "http://example.com/image.jpg",
            quantity = 2,
            orderId = 101
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
            orderId = 101
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
            orderId = 101
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
    fun `getCartItems calls getCartItemsByOrderId on dao`() {
        val orderId = 123L
        cartRepository.getCartItems(orderId)

        verify(cartItemDao).getCartItemsByOrderId(orderId)
    }
}
