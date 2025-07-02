package com.example.henryapp.ui.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.henryapp.model.data.entity.Product

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
            .padding(5.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = product.url,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Precio: $${product.price}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Green
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (quantity != 0){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Button(onClick = onDecreaseQuantity) {
                            Text("-")
                        }
                        Text(text = "$quantity", modifier = Modifier.padding(horizontal = 16.dp))
                        Button(onClick = onIncreaseQuantity) {
                            Text("+")
                        }
                    }
                    Button(onClick = { removeFromCart(product) }) {
                        Text("Eliminar")
                    }
                }
            }
            else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { onAddToCart(product, 1) }) {
                        Text("Añadir al carrito")
                    }
                }
            }

        }
    }
}