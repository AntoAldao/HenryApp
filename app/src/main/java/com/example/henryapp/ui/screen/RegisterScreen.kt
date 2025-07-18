package com.example.henryapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.henryapp.R
import com.example.henryapp.ui.componets.CustomOutlinedTextField
import com.example.henryapp.ui.theme.golden
import com.example.henryapp.ui.theme.goldenTransparent
import com.example.henryapp.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(viewModel: RegisterViewModel = hiltViewModel(), onRegisterSuccess: () -> Unit, onRegisterError: (errorMessage:String) -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

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

    LaunchedEffect(viewModel.errorMessage.value) {
        viewModel.errorMessage.value?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
            }
            viewModel.errorMessage.value = null
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        goldenTransparent,
                        golden
                    )
                )
            )
            .padding(horizontal = 16.dp),
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 0.dp),
        ) {
            //app nombre
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Henry Food",
                fontSize = 38.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // Nombre
            CustomOutlinedTextField(
                value = viewModel.name.value,
                onValueChange = { viewModel.name.value = it },
                label = "Nombre",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Apellido
            CustomOutlinedTextField(
                value = viewModel.lastName.value,
                onValueChange = { viewModel.lastName.value = it },
                label = "Apellido",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Email
            CustomOutlinedTextField(
                value = viewModel.email.value,
                onValueChange = { viewModel.email.value = it },
                label = "Email",
                modifier = Modifier.fillMaxWidth()
            )
            if (!viewModel.isEmailValid.value && viewModel.email.value.isNotEmpty()) {
                Text(
                    text = "Por favor, ingresa un email válido.",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Contraseña
            CustomOutlinedTextField(
                value = viewModel.password.value,
                onValueChange = { viewModel.password.value = it },
                label = "Contraseña",
                modifier = Modifier.fillMaxWidth(),
                isPassword = true,
            )
            if (!viewModel.isPasswordValid.value && viewModel.password.value.isNotEmpty()) {
                Text(
                    text = "La contraseña debe tener al menos 8 caracteres.",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Confirmar contraseña
            CustomOutlinedTextField(
                value = viewModel.confirmPassword.value,
                onValueChange = { viewModel.confirmPassword.value = it },
                label = "Confirmar contraseña",
                modifier = Modifier.fillMaxWidth(),
                isPassword = true,
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
            CustomOutlinedTextField(
                value = viewModel.nationality.value,
                onValueChange = { viewModel.nationality.value = it },
                label = "Nacionalidad",
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
                            onRegisterError(errorMessage)
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Black,
                    disabledContainerColor = Color.LightGray

                ),
                enabled = viewModel.isFormValid.value,
                modifier = Modifier.requiredHeight(40.dp).align(Alignment.CenterHorizontally)
            ) {
                Text("Registrarse")
            }
        }
        Image(
            painter = painterResource(id = R.drawable.hamburgesa),
            contentDescription = "Decoración",
//            contentScale = ContentScale
            modifier = Modifier
                .align(Alignment.BottomStart)
                .height(300.dp) // ajustalo según el tamaño que quieras
                .offset(x= -60.dp) // ajusta el desplazamiento según sea necesario
        )
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}