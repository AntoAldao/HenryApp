package com.example.henryapp.ui.componets

import androidx.compose.foundation.layout.PaddingValues
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
        items(products.chunked(2)) { rowItems ->  // Agrupa de 2 en 2
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxSize(),
            ) {
                rowItems.forEach { product ->
                    val cartItem = cartItems.find { it.name == product.name }
                    val quantity = cartItem?.quantity ?: 0

                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        ProductCard(
                            product = product,
                            quantity = quantity,
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
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
