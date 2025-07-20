package com.example.henryapp.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.henryapp.ui.componets.CustomOutlinedTextField
import com.example.henryapp.ui.componets.LoadingIndicator
import com.example.henryapp.ui.theme.golden
import com.example.henryapp.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    userEmail: String,
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val user by viewModel.user
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(userEmail) {
        viewModel.loadUser(userEmail)
    }

    LaunchedEffect(Unit) {
        viewModel.errorEvents.collect { errorMsg ->
            snackbarHostState.showSnackbar(errorMsg)
        }
    }

    var isEditing by remember { mutableStateOf(false) }
    var showSuccessMessage by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imagePreviewUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(user) {
        user?.let {
            name = it.name
            lastName = it.lastName
            nationality = it.nationality
            password = it.hashedPassword
            imagePreviewUrl = it.imageUrl
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> imageUri = uri }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        if (viewModel.isLoading.value) {
            LoadingIndicator()
        }else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(golden)
                    .padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedButton(
                        onClick = { navController.navigate("home/$userEmail") },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = (-10).dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color.Transparent
                        ),
                        border = BorderStroke(1.dp, Color.Transparent)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver al home",
                            tint = Color.White,
                        )
                    }


                    Card(
                        modifier = Modifier.size(120.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                    ) {
                        when {
                            imageUri != null -> Image(
                                painter = rememberAsyncImagePainter(imageUri),
                                contentDescription = "Imagen de perfil",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            !imagePreviewUrl.isNullOrEmpty() -> Image(
                                painter = rememberAsyncImagePainter(imagePreviewUrl),
                                contentDescription = "Imagen de perfil",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }


                }
                LaunchedEffect(showSuccessMessage) {
                    if (showSuccessMessage) {
                        snackbarHostState.showSnackbar("Perfil actualizado correctamente")
                        showSuccessMessage = false
                    }
                }


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                        )
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {

                        if (isEditing) {
                            OutlinedButton(
                                onClick = { imagePickerLauncher.launch("image/*") },
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                    containerColor = Color.Black,
                                ),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "Subir imagen",
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Subir Imagen", color = Color.White)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        CustomOutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = "Nombre",
                            modifier = Modifier.fillMaxWidth(),
                            enabled = isEditing,
                            borderColor = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        CustomOutlinedTextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            label = "Apellido",
                            modifier = Modifier.fillMaxWidth(),
                            enabled = isEditing,
                            borderColor = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        CustomOutlinedTextField(
                            value = userEmail,
                            onValueChange = {},
                            label = "Email",
                            modifier = Modifier.fillMaxWidth(),
                            enabled = false,
                            borderColor = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        CustomOutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = "Contraseña",
                            modifier = Modifier.fillMaxWidth(),
                            isPassword = true,
                            enabled = isEditing,
                            borderColor = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        CustomOutlinedTextField(
                            value = nationality,
                            onValueChange = { nationality = it },
                            label = "Nacionalidad",
                            modifier = Modifier.fillMaxWidth(),
                            enabled = isEditing,
                            borderColor = MaterialTheme.colorScheme.tertiary,

                        )

                        if (isEditing) {
                            Spacer(modifier = Modifier.height(130.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        viewModel.updateUser(
                                            context,
                                            name,
                                            lastName,
                                            nationality,
                                            imageUri,
                                            onSuccess = {
                                                isEditing = false
                                                showSuccessMessage = true
                                            },
                                            onError = {}
                                        )
                                    },
                                    modifier = Modifier.requiredHeight(50.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.Green,
                                        containerColor = Color.Transparent
                                    ),
                                    border = BorderStroke(2.dp, Color.Green),
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Done,
                                        contentDescription = "Confirmar",
                                        tint = Color.Green
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Confirmar", color = Color.Green)
                                }
                                OutlinedButton(
                                    onClick = { isEditing = false },
                                    modifier = Modifier.requiredHeight(50.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.Red,
                                        containerColor = Color.Transparent
                                    ),
                                    border = BorderStroke(2.dp, Color.Red),
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Cancelar",
                                        tint = Color.Red
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Cancelar", color = Color.Red)
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier.height(190.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedButton(
                                    onClick = { isEditing = true },
                                    modifier = Modifier.requiredHeight(50.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.White,
                                        containerColor = Color.Black,
                                    ),
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar perfil",
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Editar Perfil")
                                }
                                OutlinedButton(
                                    onClick = { onLogout() },
                                    modifier = Modifier.requiredHeight(50.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.White,
                                        containerColor = Color.Transparent
                                    ),
                                    border = BorderStroke(2.dp, Color.Red),
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                        contentDescription = "Cerrar sesión",
                                        tint = Color.Red
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Cerrar sesión", color = Color.Red)
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}
