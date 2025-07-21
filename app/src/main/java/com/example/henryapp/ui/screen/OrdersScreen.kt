package com.example.henryapp.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core.model.data.entity.OrderResponse
import com.example.core.util.NetworkUtils
import com.example.henryapp.navigation.BottomNavigationBar
import com.example.henryapp.ui.componets.LoadingIndicator
import com.example.henryapp.ui.componets.OrderCard
import com.example.henryapp.viewmodel.OrderViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel(),
    email: String
) {
    val orders = remember { mutableStateOf(emptyList<OrderResponse>()) }
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current
    val isConnected = NetworkUtils.isConnected(context)


    if (!isConnected) {
        NoConnectionScreen(navController = navController)
        return
    }

    LaunchedEffect(email) {
        orders.value = viewModel.getOrdersByEmail(email)
    }

    LaunchedEffect(Unit) {
        viewModel.errorEvents.collectLatest { errorMessage ->
            snackbarHostState.showSnackbar(errorMessage)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pedidos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
            ) },
        bottomBar = { BottomNavigationBar(navController, email) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        if (viewModel.isLoading.value) {
            LoadingIndicator()
        }else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                items(orders.value) { order ->
                    OrderCard(order, navController, email)
                }
            }
        }
    }
}
