package com.example.henryapp.ui.componets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.henryapp.model.data.entity.CartItem

@Composable
fun CartItemCard(item: CartItem, onIncrease: () -> Unit, onDecrease: () -> Unit, onRemove: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text(item.name, Modifier.weight(1f))
        Text("x${item.quantity}")
        Button(onClick = onIncrease) { Text("+") }
        Button(onClick = onDecrease) { Text("-") }
        Button(onClick = onRemove) { Text("Eliminar") }
    }
}