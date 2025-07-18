package com.example.henryapp.viewmodel

import com.example.core.model.data.entity.CartItem
import com.example.core.model.repository.CartRepository
import com.example.henryapp.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var repository: CartRepository
    private lateinit var viewModel: CartViewModel

    private val mockCartItemsFlow = MutableStateFlow<List<CartItem>>(emptyList())

    @Before
    fun setup() {
        repository = mock(CartRepository::class.java)
        whenever(repository.cartItems).thenReturn(mockCartItemsFlow)
        viewModel = CartViewModel(repository)
    }

    @Test
    fun `addCartItem should call repository`() = runTest {
        val item = CartItem(id = 1, name = "Test Item", price = 10.0, imageUrl = "url", quantity = 1, description = "Test Description", hasDrink = true)

        viewModel.addCartItem(item)
        advanceUntilIdle()

        verify(repository).addCartItem(item)
    }

    @Test
    fun `updateCartItem should call repository`() = runTest {
        val item = CartItem(id = 1, name = "Test Item", price = 10.0, imageUrl = "url", quantity = 1, description = "Test Description", hasDrink = true)

        viewModel.addCartItem(item)

        val itemUpdate = CartItem(id = 1, name = "Test Item", price = 10.0, imageUrl = "url", quantity = 1, description = "Test Description", hasDrink = true)

        viewModel.updateCartItem(itemUpdate)
        advanceUntilIdle()

        verify(repository).updateCartItem(itemUpdate)
    }

}
