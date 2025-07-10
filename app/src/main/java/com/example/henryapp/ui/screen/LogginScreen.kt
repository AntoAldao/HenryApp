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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.henryapp.R
import com.example.henryapp.ui.componets.CustomOutlinedTextField
import com.example.henryapp.ui.theme.golden
import com.example.henryapp.ui.theme.goldenTransparent
import com.example.henryapp.viewmodel.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel, onLoginSuccess: () -> Unit, navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val loginState by viewModel.loginResult.observeAsState()

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

            // App name
            Text(
                text = "Henry Food",
                fontSize = 38.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email
            CustomOutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = "Email",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Password
            CustomOutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label ="Contraseña",
                isPassword = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Login button
            Button(
                onClick = { viewModel.login(email.value, password.value) },
                modifier = Modifier
                    .requiredHeight(40.dp)
                    .align(Alignment.CenterHorizontally) ,
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
                onLoginSuccess()
            } else if (loginState == false) {
                Text(
                    text = "Login fallido, por favor intenta de nuevo.",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
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
    }
}
