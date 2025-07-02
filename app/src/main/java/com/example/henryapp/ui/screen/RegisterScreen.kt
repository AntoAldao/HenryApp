package com.example.henryapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.henryapp.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(viewModel: RegisterViewModel = hiltViewModel(), onRegisterSuccess: () -> Unit) {
    LaunchedEffect(
        key1 = listOf(
            viewModel.name.value,
            viewModel.lastName.value,
            viewModel.email.value,
            viewModel.password.value,
            viewModel.confirmPassword.value,
            viewModel.nationality.value
        )
    ) {
        viewModel.validateForm()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Nombre
        TextField(
            value = viewModel.name.value,
            onValueChange = { viewModel.name.value = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Apellido
        TextField(
            value = viewModel.lastName.value,
            onValueChange = { viewModel.lastName.value = it },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Email
        TextField(
            value = viewModel.email.value,
            onValueChange = { viewModel.email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        if (!viewModel.isEmailValid.value) {
            Text(
                text = "Por favor, ingresa un email válido.",
                color = Color.Red,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Contraseña
        TextField(
            value = viewModel.password.value,
            onValueChange = { viewModel.password.value = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        if (!viewModel.isPasswordValid.value) {
            Text(
                text = "La contraseña debe tener al menos 8 caracteres.",
                color = Color.Red,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Confirmar contraseña
        TextField(
            value = viewModel.confirmPassword.value,
            onValueChange = { viewModel.confirmPassword.value = it },
            label = { Text("Confirmar contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        if (!viewModel.isPasswordMatch.value) {
            Text(
                text = "Las contraseñas no coinciden.",
                color = Color.Red,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Nacionalidad
        TextField(
            value = viewModel.nationality.value,
            onValueChange = { viewModel.nationality.value = it },
            label = { Text("Nacionalidad") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botón de registro
        Button(
            onClick = {
                viewModel.onRegister(
                    onSuccess = {
                        onRegisterSuccess()
                    },
                    onError = { errorMessage ->
                        //TODO agregar manejo de errores
                    }
                )
                onRegisterSuccess()
            },
            enabled = viewModel.isFormValid.value,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
    }
}
