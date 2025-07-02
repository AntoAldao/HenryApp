package com.example.henryapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.henryapp.ui.componets.CartItemCard
import com.example.henryapp.viewmodel.CartViewModel
import com.example.henryapp.viewmodel.OrderViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, viewModel: CartViewModel = hiltViewModel(), orderViewModel: OrderViewModel = hiltViewModel(), email:String) {
    val cartItems = viewModel.cartItems.collectAsState().value
    val total = cartItems.sumOf { it.price * it.quantity }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Carrito") })
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) { item ->
                    CartItemCard(
                        item = item,
                        onIncrease = { viewModel.updateCartItem(item.copy(quantity = item.quantity + 1)) },
                        onDecrease = {
                            if (item.quantity > 1) viewModel.updateCartItem(item.copy(quantity = item.quantity - 1))
                        },
                        onRemove = { viewModel.removeCartItem(item) }
                    )
                }
            }
            Text(
                text = "Total: $${"%.2f".format(total)}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
            val coroutineScope = rememberCoroutineScope()
            Button(
                onClick = {
                    coroutineScope.launch {
                        val orderId = orderViewModel.addOrder(total, email)
                        viewModel.cartItems.value.forEach { item ->
                            viewModel.updateCartItem(item.copy(orderId = orderId))
                        }
                        navController.navigate("orders/$email")
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Hacer Pedido")
            }
        }
    }
}

