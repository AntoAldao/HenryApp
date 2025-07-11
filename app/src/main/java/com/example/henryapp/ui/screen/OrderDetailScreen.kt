package com.example.henryapp.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.henryapp.model.data.entity.CartItem
import com.example.henryapp.ui.componets.OrdersItemsCards
import com.example.henryapp.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    navController: NavController,
    orderId: Long,
    viewModel: OrderViewModel,
    email: String
) {

    println("orderId : $orderId")

    val cartItems = remember { mutableStateOf(emptyList<CartItem>()) }

    LaunchedEffect(orderId) {
        cartItems.value = viewModel.getCardItems(orderId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del pedido") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("orders/$email") {
                            popUpTo("orders/$email") { inclusive = true }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(cartItems.value) { cartItem ->
                OrdersItemsCards(cartItem)
            }
        }
    }
}