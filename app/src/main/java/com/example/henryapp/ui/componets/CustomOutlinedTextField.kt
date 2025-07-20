package com.example.henryapp.ui.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    isPassword: Boolean = false,
    borderColor: Color = Color.White,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cornerRadius))
            .background(Color.Transparent),
        shape = RoundedCornerShape(cornerRadius),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = borderColor,
            focusedBorderColor = borderColor,
            cursorColor = borderColor,
            focusedLabelColor = borderColor,
            unfocusedLabelColor =MaterialTheme.colorScheme.secondary,
            focusedTextColor = borderColor,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            disabledTextColor = MaterialTheme.colorScheme.tertiary,
            disabledLabelColor = MaterialTheme.colorScheme.tertiary,
            disabledBorderColor = borderColor
        ),
        enabled = enabled,
        textStyle = TextStyle(
            color = borderColor,
            fontSize = 16.sp
        )

    )
}
