package com.example.henryapp.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core.model.data.entity.Order
import com.example.henryapp.navigation.BottomNavigationBar
import com.example.henryapp.ui.componets.OrderCard
import com.example.henryapp.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel(),
    email: String
) {
    val orders = remember { mutableStateOf(emptyList<Order>()) }
    LaunchedEffect(email) {
        orders.value = viewModel.getOrdersByEmail(email)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Pedidos") }) },
        bottomBar = { BottomNavigationBar(navController, email) }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(orders.value) { order ->
                OrderCard(order, navController, email)
            }
        }
    }
}