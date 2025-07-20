package com.example.henryapp.ui.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PriceFilterDialog(
    currentMinPrice: Double?,
    currentMaxPrice: Double?,
    onDismiss: () -> Unit,
    onApply: (Double, Double) -> Unit,
    onClear: () -> Unit
){
    val minPriceInput = remember(currentMinPrice) { mutableStateOf(currentMinPrice?.toString() ?: "") }
    val maxPriceInput = remember(currentMaxPrice) { mutableStateOf(currentMaxPrice?.toString() ?: "") }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Filtrar por precio") },
        backgroundColor = MaterialTheme.colorScheme.secondary,
        text = {
            Column {
                OutlinedTextField(
                    value = minPriceInput.value,
                    onValueChange = { minPriceInput.value = it },
                    label = { Text("Precio mínimo") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = maxPriceInput.value,
                    onValueChange = { maxPriceInput.value = it },
                    label = { Text("Precio máximo") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val minPrice = minPriceInput.value.toDoubleOrNull() ?: 0.0
                val maxPrice = maxPriceInput.value.toDoubleOrNull() ?: Double.MAX_VALUE
                onApply(minPrice, maxPrice)
            }) {
                Text("Aplicar")
            }
        },
        dismissButton = {
            Row {
                if (currentMinPrice != null || currentMaxPrice != null) {
                    TextButton(onClick = {
                        onClear()
                        onDismiss()
                    }) {
                        Text("Borrar")
                    }
                }
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }

            }
        }
    )
}