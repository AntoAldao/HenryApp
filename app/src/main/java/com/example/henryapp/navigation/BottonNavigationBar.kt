package com.example.henryapp.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController


@Composable
fun BottomNavigationBar(navController: NavController, email: String) {
    val currentDestination = remember { mutableStateOf("home") }
    BottomNavigation{
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            selected = currentDestination.value == "home",
            onClick = { navController.navigate("home/$email") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.List, contentDescription = "Pedidos") },
            selected = currentDestination.value == "orders",
            onClick = { navController.navigate("orders/$email") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            selected = currentDestination.value == "profile",
            onClick = { navController.navigate("profile/${email}") }
        )
    }
}