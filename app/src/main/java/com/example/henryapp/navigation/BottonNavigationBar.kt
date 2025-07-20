package com.example.henryapp.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.henryapp.ui.theme.golden

@Composable
fun BottomNavigationBar(navController: NavController, email: String) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = golden
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.List, contentDescription = "Pedidos") },
            selected = currentRoute?.startsWith("orders") == true,
            onClick = { navController.navigate("orders/$email") },
            selectedContentColor = MaterialTheme.colorScheme.secondary,
            unselectedContentColor = Color.Black
        )

        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            selected = currentRoute?.startsWith("home") == true,
            onClick = { navController.navigate("home/$email") },
            selectedContentColor = MaterialTheme.colorScheme.secondary,
            unselectedContentColor = Color.Black
        )

        BottomNavigationItem(
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart") },
            selected = currentRoute?.startsWith("cart") == true,
            onClick = { navController.navigate("cart/$email") },
            selectedContentColor = MaterialTheme.colorScheme.secondary,
            unselectedContentColor = Color.Black
        )

        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            selected = currentRoute?.startsWith("profile") == true,
            onClick = { navController.navigate("profile/$email") },
            selectedContentColor = MaterialTheme.colorScheme.secondary,
            unselectedContentColor = Color.Black
        )
    }
}
