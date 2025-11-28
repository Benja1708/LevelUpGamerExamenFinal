package com.example.levelupgamer.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.R
import com.example.levelupgamer.viewmodel.LoginViewModel
import com.example.levelupgamer.viewmodel.UserViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    loginViewModel: LoginViewModel = viewModel()
) {
    val loginUiState by loginViewModel.uiState.collectAsState()

    val neonGreen = Color(0xFF39FF14)
    val darkGray = Color(0xFF1F1F1F)
    val lightGray = Color(0xFFD3D3D3)
    val onSubmitSuccess = { email: String, password: String ->
        userViewModel.loginByEmail(email, password) { isUserLoaded ->
            if (isUserLoaded) {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 32.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "LevelUp Gamer Logo",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "INICIAR SESIÓN",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = neonGreen,
            fontFamily = FontFamily.Default
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = loginUiState.username,
            onValueChange = { newValue ->
                loginViewModel.onUsernameChange(newValue)
            },
            label = { Text("Correo Electrónico", color = lightGray) },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = neonGreen) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = neonGreen,
                unfocusedBorderColor = lightGray.copy(alpha = 0.5f),
                cursorColor = neonGreen,
                focusedContainerColor = darkGray,
                unfocusedContainerColor = darkGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = loginUiState.password,
            onValueChange = { newValue ->
                loginViewModel.onPasswordChange(newValue)
            },
            label = { Text("Contraseña", color = lightGray) },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = neonGreen) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = neonGreen,
                unfocusedBorderColor = lightGray.copy(alpha = 0.5f),
                cursorColor = neonGreen,
                focusedContainerColor = darkGray,
                unfocusedContainerColor = darkGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        if (loginUiState.error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = loginUiState.error!!,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { loginViewModel.submit(onSubmitSuccess) },
            enabled = !loginUiState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = neonGreen,
                contentColor = Color.Black
            )
        ) {
            if (loginUiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.Black
                )
            } else {
                Text("INICIAR SESIÓN", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "¿No tienes cuenta? ",
                color = lightGray,
                fontSize = 14.sp
            )
            Text(
                text = "Regístrate aquí",
                color = neonGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navController.navigate("register") }
            )
        }
    }
}