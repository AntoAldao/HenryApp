package com.example.henryapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.core.util.NetworkUtils
import com.example.henryapp.R
import com.example.henryapp.preference.SessionManager
import com.example.henryapp.ui.componets.CustomOutlinedTextField
import com.example.henryapp.ui.theme.golden
import com.example.henryapp.ui.theme.goldenTransparent
import com.example.henryapp.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(viewModel: LoginViewModel, onLoginSuccess: () -> Unit, navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val loginState by viewModel.loginResult.observeAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current
    val isConnected = NetworkUtils.isConnected(context)


    if (!isConnected) {
        NoConnectionScreen(navController = navController)
        return
    }

    LaunchedEffect(Unit) {
        viewModel.errorEvents.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(goldenTransparent, golden)
                )
            )
            .padding(horizontal = 16.dp)
            .padding(top = 160.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Henry Food",
                fontSize = 38.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomOutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = "Email",
                modifier = Modifier.fillMaxWidth(),
                borderColor = Color.Black,
            )
            Spacer(modifier = Modifier.height(8.dp))

            CustomOutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label ="Contraseña",
                isPassword = true,
                modifier = Modifier.fillMaxWidth(),
                borderColor = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.login(email.value, password.value) },
                modifier = Modifier
                    .requiredHeight(40.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Black,
                    disabledContainerColor = Color.LightGray
                ),
                enabled = email.value.isNotBlank() && password.value.isNotBlank(),
            ) {
                Text("Iniciar Sesión")
            }

            if (loginState == true) {
                SessionManager.saveUserEmail(viewModel.appContext, email.value)
                onLoginSuccess()
            }

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                buildAnnotatedString {
                    append("¿No tienes cuenta? ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    ) {
                        append("Regístrate ahora")
                    }
                },
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier.clickable { navController.navigate("register") }
            )
        }

        Image(
            painter = painterResource(id = R.drawable.hamburgesa),
            contentDescription = "Decoración",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .height(300.dp)
                .offset(x = (-60).dp)
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}