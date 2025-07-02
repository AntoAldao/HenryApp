package com.example.henryapp.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.henryapp.navigation.BottomNavigationBar
import com.example.henryapp.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    userEmail: String,  // Recibimos el email para cargar el usuario
    viewModel: ProfileViewModel = hiltViewModel()
) {
    // Cargar usuario solo una vez al entrar
    LaunchedEffect(userEmail) {
        viewModel.loadUser(userEmail)
    }

    val user by viewModel.user

    // Maneja los campos del formulario
    var name by remember { mutableStateOf(user?.name?: "") }
    var lastName by remember { mutableStateOf(user?.lastName?:"") }
    var emailState by remember { mutableStateOf(userEmail)}
    var password by remember { mutableStateOf(user?.hashedPassword ?: "") }
    var nationality by remember { mutableStateOf(user?.nationality ?: "") }
    var imageUri by remember { mutableStateOf<Uri?>(user?.imageUrl?.let { Uri.parse(it) }) }
    var imagePreviewUrl by remember { mutableStateOf<String?>(user?.imageUrl ?: "") }

    // Contexto para la subida de imágenes
    val context = LocalContext.current

    // Actualiza los valores de los campos cuando el usuario cambia
    LaunchedEffect(user) {
        user?.let {
            name = it.name
            lastName = it.lastName
            emailState = it.email
            nationality = it.nationality
            imagePreviewUrl = it.imageUrl // Previsualizar imagen almacenada
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Perfil") })
        },
        bottomBar = {
            BottomNavigationBar(navController, userEmail)
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image Upload
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .size(120.dp)
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.LightGray)
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (!imagePreviewUrl.isNullOrEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(imagePreviewUrl),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = "Cargar Imagen",
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text("Cargar Imagen")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Form Fields
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = emailState,
                onValueChange = { emailState = it },
                label = { Text("Email") },
                enabled = false, // Deshabilitado para evitar cambios en el correo
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = nationality,
                onValueChange = { nationality = it },
                label = { Text("Nacionalidad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.updateUser(
                        context = context,
                        name = name,
                        lastName = lastName,
                        nationality = nationality,
                        imageUri = imageUri,
                        onSuccess = {
                            // Muestra un mensaje de éxito
                            println("Perfil actualizado correctamente.")
                        },
                        onError = { error ->
                            // Maneja el error,
                            println("Error: $error")
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cambios")
            }
        }
    }
}