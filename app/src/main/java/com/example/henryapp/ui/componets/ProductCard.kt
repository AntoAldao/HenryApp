package com.example.henryapp.ui.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
    removeFromCart: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 2.dp)
            )

            Text(
                text = product.name ?: "", // cambiar a descripción si es necesario
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // Botones de cantidad o Añadir
                if (quantity > 0) {
                    Row {
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
                        modifier = Modifier.align(Alignment.Bottom),
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
