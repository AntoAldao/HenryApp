package com.example.henryapp.ui.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.core.model.data.entity.Product
import com.example.henryapp.ui.theme.golden

@Composable
fun ProductCard(
    product: Product,
    quantity: Int,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onAddToCart: (Product, Int) -> Unit,
    removeFromCart: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp) // 👈 altura fija para toda la tarjeta
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp) // 👈 altura fija para la imagen
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                modifier = Modifier.padding(vertical = 2.dp)
            )

            // 👇 Scroll SOLO en la descripción
            Box(
                modifier = Modifier
                    .weight(1f) // toma el espacio sobrante
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = product.description ?: "",
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.secondary),
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (quantity > 0) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = onDecreaseQuantity,
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(containerColor = golden)
                        ) {
                            Text("-", color = Color.Black)
                        }

                        Text(
                            text = "$quantity",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Button(
                            onClick = onIncreaseQuantity,
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(containerColor = golden)
                        ) {
                            Text("+", color = Color.Black)
                        }
                    }
                } else {
                    Text(
                        text = "$${product.price}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = golden,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Button(
                        onClick = { onAddToCart(product, 1) },
                        shape = RoundedCornerShape(100),
                        colors = ButtonDefaults.buttonColors(containerColor = golden)
                    ) {
                        Text("+", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
