package com.example.henryapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core.model.data.entity.CartItem
import com.example.henryapp.navigation.BottomNavigationBar
import com.example.henryapp.ui.componets.ProductList
import com.example.henryapp.ui.theme.golden
import com.example.henryapp.viewmodel.CartViewModel
import com.example.henryapp.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    cartViewModel: CartViewModel = hiltViewModel(),
    email: String
) {
    val cartItems = cartViewModel.cartItems.collectAsState()
    val categories = listOf("All", "Combos", "Sliders", "Classics", "Veggie", "Chicken", "Beef", "Fish", "Desserts")
    val selectedCategoryIndex = remember { mutableIntStateOf(0) }
    val snackbarHostState = remember { androidx.compose.material3.SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.errorEvents.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(Unit) {
        cartViewModel.errorEvents.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, email)
        },
        snackbarHost = {
            androidx.compose.material3.SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Text(
                text = "Henry Food",
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .padding(horizontal = 15.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = viewModel.searchQuery.value,
                    onValueChange = { viewModel.filterProducts(it) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    placeholder = { Text("Search") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    shape = RoundedCornerShape(24.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray,
                    )
                )
                IconButton(
                    onClick = { /* filtro pendiente */ },
                    modifier = Modifier
                        .padding(4.dp)
                        .size(48.dp)
                        .background(color = golden, shape = RoundedCornerShape(12.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Filter",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

//            LazyRow(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 12.dp),
//                contentPadding = PaddingValues(horizontal = 12.dp),
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(categories.size) { index ->
//                    val category = categories[index]
//                    Box(
//                        modifier = Modifier
//                            .background(
//                                color = if (index == selectedCategoryIndex.intValue) golden else Color.LightGray,
//                                shape = RoundedCornerShape(20.dp)
//                            )
//                            .padding(horizontal = 16.dp, vertical = 8.dp)
//                            .clickable { selectedCategoryIndex.intValue = index }
//                    ) {
//                        Text(
//                            text = category,
//                            color = if (index == selectedCategoryIndex.intValue) Color.White else Color.Black
//                        )
//                    }
//                }
//            }

            ProductList(
                products = viewModel.products,
                cartItems = cartItems.value,
                onProductClick = { product ->
                    navController.navigate("product/${product.id}")
                },
                onAddToCart = { product, quantity ->
                    cartViewModel.addCartItem(
                        CartItem(
                            name = product.name,
                            price = product.price,
                            imageUrl = product.imageUrl,
                            quantity = quantity,
                            hasDrink = product.hasDrink,
                            description = product.description ?: "",
                        )
                    )
                },
                onIncreaseQuantity = { cartItem ->
                    cartViewModel.updateCartItem(cartItem.copy(quantity = cartItem.quantity + 1))
                },
                onDecreaseQuantity = { cartItem ->
                    if (cartItem.quantity > 1) {
                        cartViewModel.updateCartItem(cartItem.copy(quantity = cartItem.quantity - 1))
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