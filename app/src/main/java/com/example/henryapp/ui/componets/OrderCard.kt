package com.example.henryapp.ui.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.henryapp.model.data.entity.CartItem
import com.example.henryapp.model.data.entity.Order

@Composable
fun OrderCard(order: Order, cartItems: List<CartItem>) {

    Column(Modifier.padding(16.dp)) {
        Text("Pedido #${order.id}", style = MaterialTheme.typography.titleMedium)
        Text("Total: $${"%.2f".format(order.total)}", style = MaterialTheme.typography.bodyMedium)
        cartItems.forEach { item ->
            Text(
                text = "${item.name} x${item.quantity} - $${"%.2f".format(item.price)}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        Text("Fecha: ${java.text.SimpleDateFormat("dd/MM/yyyy").format(order.date)}")
    }
}