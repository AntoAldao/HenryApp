package com.example.henryapp.ui.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core.model.data.entity.CartItem

@Composable
fun OrdersItemsCards(item: CartItem) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
            Row(
                modifier = Modifier
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.name,
                    modifier = Modifier
                        .height(50.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("${item.name} x ${item.quantity}" , fontWeight = FontWeight.Bold)
                    Text("$${"%.2f".format(item.price)}", color = Color.Black, fontWeight = FontWeight.Bold)
                }
                Text("Total: $${"%.2f".format(item.price*item.quantity)}", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
