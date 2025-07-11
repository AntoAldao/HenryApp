package com.example.henryapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.henryapp.ui.screen.CartScreen
import com.example.henryapp.ui.screen.HomeScreen
import com.example.henryapp.ui.screen.LoginScreen
import com.example.henryapp.ui.screen.OrderDetailScreen
import com.example.henryapp.ui.screen.OrdersScreen
import com.example.henryapp.ui.screen.ProfileScreen
import com.example.henryapp.ui.screen.RegisterScreen
import com.example.henryapp.viewmodel.CartViewModel
import com.example.henryapp.viewmodel.HomeViewModel
import com.example.henryapp.viewmodel.LoginViewModel
import com.example.henryapp.viewmodel.OrderViewModel
import com.example.henryapp.viewmodel.ProductViewModel
import com.example.henryapp.viewmodel.ProfileViewModel
import com.example.henryapp.viewmodel.RegisterViewModel

@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val loginViewModel: LoginViewModel = viewModel()

            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    val email = loginViewModel.userEmail.value
                    navController.navigate("home/$email") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                navController = navController
            )
        }
        composable("home/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val homeViewModel: HomeViewModel = viewModel()
            val productViewModel: ProductViewModel = viewModel()
            val cartViewModel: CartViewModel = hiltViewModel()
            HomeScreen(navController, homeViewModel, productViewModel, cartViewModel, email)
        }
        composable("orders/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val ordersViewModel: OrderViewModel = hiltViewModel()
            OrdersScreen(navController, ordersViewModel, email)
        }
        composable("profile/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val profileViewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(navController, email, profileViewModel,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("home/{email}") { inclusive = true }
                    }
                }
            )
        }
        composable("cart/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val cartViewModel: CartViewModel = hiltViewModel()
            val orderViewModel: OrderViewModel = hiltViewModel()
            CartScreen(navController, cartViewModel, orderViewModel, email)
        }
        composable("register") {
            val registerViewModel: RegisterViewModel = hiltViewModel()

            RegisterScreen(viewModel = registerViewModel,
                onRegisterSuccess = {
                    val email = registerViewModel.email.value // Retrieve email from ViewModel
                    navController.navigate("home/$email") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }
        composable("orderDetail/{email}/{orderId}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val orderId = backStackEntry.arguments?.getString("orderId")?.toLong() ?: 0L
            val orderViewModel: OrderViewModel = hiltViewModel()
            OrderDetailScreen(navController, orderId, orderViewModel, email)
        }
    }
}