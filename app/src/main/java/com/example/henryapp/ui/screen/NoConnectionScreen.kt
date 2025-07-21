package com.example.henryapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.henryapp.preference.SessionManager

@Composable
fun NoConnectionScreen(navController: NavController) {
    val context = LocalContext.current
    val email = remember { SessionManager.getUserEmail(context) ?: "" }

    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Default.WarningAmber,
                contentDescription = "No connection",
                modifier = Modifier.size(120.dp),
                tint = Color.Yellow
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Sin conexión a Internet",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Por favor verifica tu conexión y vuelve a intentarlo.",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                val currentRoute = navController.currentBackStackEntry?.destination?.route

                currentRoute?.let { route ->
                    val newRoute = if (route.contains("{email}")) {
                        route.replace("{email}", email)
                    } else if (route.contains(Regex("email=[^/]+"))) {
                        route.replace(Regex("email=[^/]+"), "email=$email")
                    } else {
                        route
                    }

                    navController.navigate(newRoute) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }) {
                Text(
                    text = "Reintentar",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}