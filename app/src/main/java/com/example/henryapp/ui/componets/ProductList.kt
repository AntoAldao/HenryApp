package com.example.henryapp.ui.componets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.model.data.entity.CartItem
import com.example.core.model.data.entity.Product

@Composable
fun ProductList(
    products: List<Product>,
    cartItems: List<CartItem>,
    onProductClick: (Product) -> Unit,
    onAddToCart: (Product, Int) -> Unit,
    onIncreaseQuantity: (CartItem) -> Unit,
    onDecreaseQuantity: (CartItem) -> Unit,
    removeFromCart: (CartItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(products.chunked(2)) { rowItems ->
            Row(modifier = Modifier.fillMaxSize()) {
                rowItems.forEach { product ->
                    val cartItem = cartItems.find { it.name == product.name }
                    val quantity = cartItem?.quantity ?: 0

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        ProductCard(
                            product = product,
                            quantity = quantity,
                            modifier = Modifier.clickable { onProductClick(product) },
                            onIncreaseQuantity = {
                                if (cartItem != null) {
                                    onIncreaseQuantity(cartItem)
                                } else {
                                    onAddToCart(product, 1)
                                }
                            },
                            onDecreaseQuantity = {
                                if (cartItem != null && cartItem.quantity > 0) {
                                    onDecreaseQuantity(cartItem)
                                }
                            },
                            onAddToCart = { prod, qty -> onAddToCart(prod, qty) },
                            removeFromCart = {
                                if (cartItem != null) {
                                    removeFromCart(cartItem)
                                }
                            }
                        )
                    }
                }

                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

