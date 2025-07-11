package com.example.henryapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.henryapp.ui.componets.CartItemCard
import com.example.henryapp.viewmodel.CartViewModel
import com.example.henryapp.viewmodel.OrderViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel = hiltViewModel(),
    email: String
) {
    val cartItems = viewModel.cartItems.collectAsState().value
    val total = cartItems.sumOf { it.price * it.quantity }
    val coroutineScope = rememberCoroutineScope()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            OutlinedButton(
                onClick = { navController.navigate("home/$email") },
                modifier = Modifier.offset(x = (-10).dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Transparent
                ),
                border = BorderStroke(1.dp, Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver al home",
                    tint = Color.Black,
                )
            }

            if (cartItems.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Carrito vacío",
                        tint = Color.LightGray,
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .height(140.dp)
                            .fillMaxWidth(0.4f)
                    )
                    Text(
                        text = "Tu carrito está vacío",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
                        color = Color.Gray
                    )
                    Button(
                        onClick = { navController.navigate("home/$email") },
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Empezar a pedir")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    items(cartItems) { item ->
                        CartItemCard(
                            item = item,
                            onIncrease = {
                                viewModel.updateCartItem(item.copy(quantity = item.quantity + 1))
                            },
                            onDecrease = {
                                if (item.quantity > 1) {
                                    viewModel.updateCartItem(item.copy(quantity = item.quantity - 1))
                                }
                            },
                            onRemove = {
                                viewModel.removeCartItem(item)
                            },
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                    )
                    Text(
                        text = "$${"%.2f".format(total)}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            val orderId = orderViewModel.addOrder(total, email)
                            cartItems.forEach { item ->
                                viewModel.updateCartItem(item.copy(orderId = orderId))
                            }
                            navController.navigate("orders/$email")
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color.Black,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Pedir",
                        tint = Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = "Pedir", color = Color.White)
                }
            }
        }
    }
}


