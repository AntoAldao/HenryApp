package com.example.henryapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.core.worker.SyncWorker
import com.example.henryapp.preference.SessionManager
import com.example.henryapp.ui.screen.CartScreen
import com.example.henryapp.ui.screen.HomeScreen
import com.example.henryapp.ui.screen.LoginScreen
import com.example.henryapp.ui.screen.OrderDetailScreen
import com.example.henryapp.ui.screen.OrdersScreen
import com.example.henryapp.ui.screen.ProductsDetailsScreen
import com.example.henryapp.ui.screen.ProfileScreen
import com.example.henryapp.ui.screen.RegisterScreen
import com.example.henryapp.viewmodel.CartViewModel
import com.example.henryapp.viewmodel.HomeViewModel
import com.example.henryapp.viewmodel.LoginViewModel
import com.example.henryapp.viewmodel.OrderViewModel
import com.example.henryapp.viewmodel.ProfileViewModel
import com.example.henryapp.viewmodel.RegisterViewModel
import com.example.henryapp.viewmodel.ThemeViewModel
import java.util.concurrent.TimeUnit

@Composable
fun MainApp(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "SyncWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }

    NavHost(navController = navController, startDestination = "root") {
        composable("root") {
            LaunchedEffect(Unit) {
                val savedEmail = SessionManager.getUserEmail(context)
                if (!savedEmail.isNullOrEmpty()) {
                    navController.navigate("home/$savedEmail") {
                        popUpTo("root") { inclusive = true }
                    }
                } else {
                    navController.navigate("login") {
                        popUpTo("root") { inclusive = true }
                    }
                }
            }
        }

        composable("login") {
            val loginViewModel: LoginViewModel = hiltViewModel()
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
            val homeViewModel: HomeViewModel = hiltViewModel()
            val cartViewModel: CartViewModel = hiltViewModel()
            HomeScreen(navController, homeViewModel, cartViewModel, themeViewModel, email)
        }

        composable("product/{id}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("id") ?: ""
            val cartViewModel: CartViewModel = hiltViewModel()
            val homeViewModel: HomeViewModel = hiltViewModel()
            ProductsDetailsScreen(navController, cartViewModel, homeViewModel, productId)
        }

        composable("orders/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val ordersViewModel: OrderViewModel = hiltViewModel()
            OrdersScreen(navController, ordersViewModel, email)
        }

        composable("profile/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val profileViewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                navController, email, profileViewModel,
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
            RegisterScreen(
                viewModel = registerViewModel,
                onRegisterSuccess = {
                    val email = registerViewModel.email.value
                    SessionManager.saveUserEmail(context, email)
                    navController.navigate("home/$email") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onRegisterError = { errorMessage ->
                },
                navController = navController
            )
        }

        composable("orderDetail/{email}/{orderId}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            val orderViewModel: OrderViewModel = hiltViewModel()
            OrderDetailScreen(navController, orderId, orderViewModel, email)
        }
    }
}
