package com.example.henryapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.henryapp.model.data.entity.CartItem
import com.example.henryapp.navigation.BottomNavigationBar
import com.example.henryapp.ui.componets.ProductList
import com.example.henryapp.viewmodel.CartViewModel
import com.example.henryapp.viewmodel.HomeViewModel
import com.example.henryapp.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel,productViewModel: ProductViewModel,cartViewModel: CartViewModel = hiltViewModel(), email:String) {
    val cartItems = cartViewModel.cartItems.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    IconButton(onClick = { navController.navigate("cart/$email") }) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController,email)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = viewModel.searchQuery.value,
                onValueChange = { viewModel.filterProducts(it) },
                label = { Text("Buscar productos") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            ProductList(
                products = viewModel.products,
                cartItems = cartItems.value,
                onProductClick = { product ->
                    navController.navigate("product/${product.id}")
                },
                onAddToCart = { product, quantity ->
                    cartViewModel.addCartItem(
                        CartItem(
                            id = product.id,
                            name = product.name,
                            price = product.price,
                            imageUrl = product.url,
                            quantity = quantity,
                            orderId = 0
                        )
                    )
                },
                onIncreaseQuantity = { cartItem ->
                    cartViewModel.updateCartItem(
                        cartItem.copy(quantity = cartItem.quantity + 1)
                    )
                },
                onDecreaseQuantity = { cartItem ->
                    if (cartItem.quantity > 1) {
                        cartViewModel.updateCartItem(
                            cartItem.copy(quantity = cartItem.quantity - 1)
                        )
                    } else {
                        cartViewModel.removeCartItem(cartItem)
                    }
                },
                removeFromCart = { cartItem ->
                    cartViewModel.removeCartItem(cartItem)
                }
            )
        }
    }
}