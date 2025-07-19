package com.example.henryapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.core.model.data.entity.CartItem
import com.example.core.model.data.entity.Product
import com.example.henryapp.ui.componets.LoadingIndicator
import com.example.henryapp.ui.theme.golden
import com.example.henryapp.viewmodel.CartViewModel
import com.example.henryapp.viewmodel.HomeViewModel

@Composable
fun ProductsDetailsScreen(
    navController: NavController,
    viewModel: CartViewModel,
    homeViewModel: HomeViewModel,
    productId: String,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val cartItems = viewModel.cartItems.collectAsState()
    val productState = remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(Unit) {
        viewModel.errorEvents.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(productId) {
        productState.value = homeViewModel.getProductById(productId)
    }

    if (homeViewModel.isLoading.value) {
        LoadingIndicator()
    } else {
        productState.value?.let { product ->
            val existingCartItem = cartItems.value.find { it.name == product.name }
            val quantity = remember(existingCartItem?.quantity) {
                mutableStateOf(existingCartItem?.quantity ?: 1)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }

                Spacer(modifier = Modifier.height(8.dp))

                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .border(BorderStroke(2.dp, golden), shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = product.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = product.description ?: "No description available.",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Precio unitario: $${String.format("%.2f", product.price)}",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    if (existingCartItem != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = {
                                    if (quantity.value > 1) quantity.value--
                                    if (existingCartItem.quantity > 1) {
                                        viewModel.updateCartItem(existingCartItem.copy(quantity = existingCartItem.quantity - 1))
                                    } else {
                                        viewModel.removeCartItem(existingCartItem)
                                    }
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(golden, CircleShape)
                            ) {
                                Text("-", color = Color.Black, fontSize = 20.sp)
                            }

                            Text(
                                text = quantity.value.toString(),
                                fontSize = 16.sp,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )

                            IconButton(
                                onClick = {
                                    quantity.value++
                                    viewModel.updateCartItem(existingCartItem.copy(quantity = existingCartItem.quantity + 1))
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(golden, CircleShape)
                            ) {
                                Text("+", color = Color.Black, fontSize = 20.sp)
                            }
                            Spacer(modifier = Modifier.height(12.dp).width(12.dp))
                            Row(
                                modifier = Modifier
                                    .height(48.dp)
                                    .background(golden, shape = RoundedCornerShape(50))
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Total: $${String.format("%.2f", product.price * quantity.value)}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }

                        }
                    }else {


                        Button(
                            onClick = {

                                viewModel.addCartItem(
                                    CartItem(
                                        name = product.name,
                                        price = product.price,
                                        imageUrl = product.imageUrl,
                                        quantity = quantity.value,
                                        hasDrink = product.hasDrink,
                                        description = product.description ?: "",
                                    )
                                )

                            },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(containerColor = golden),
                            modifier = Modifier
                                .padding(8.dp)
                                .height(48.dp)
                        ) {
                            Text(
                                text =
                                    "Añadir al carrito",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}