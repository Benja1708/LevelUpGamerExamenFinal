package com.example.levelupgamer.ui.screens.welcome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelupgamer.R

@Composable
fun WelcomeScreen(navController: NavController) {

    val neonGreen = Color(0xFF39FF14) // Tu color de acento

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Fondo oscuro
            .padding(horizontal = 32.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // 1. SECCIÓN PRINCIPAL (LOGO Y TÍTULO)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 80.dp) // Empuja el bloque Logo/Título 80dp hacia abajo
        ) {

            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "LevelUP-Gamer Logo",
                modifier = Modifier
                    .size(130.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "¡Bienvenido a LevelUP-Gamer!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = neonGreen,
                fontFamily = FontFamily.Default,
                // FIX: Aseguramos que el texto ocupe todo el ancho para centrarse correctamente.
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tu tienda gamer favorita. Accede a los mejores productos, noticias y eventos.",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f),
                fontFamily = FontFamily.Default,
                // FIX: Aplicamos fillMaxWidth y TextAlign.Center también al subtítulo.
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(48.dp))
        }

        // 2. BOTONES DE ACCIÓN PRINCIPALES
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Botón Principal: INICIAR SESIÓN (Relleno)
            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = neonGreen,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    "Iniciar Sesión",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Secundario: REGISTRARSE (Contorno Neón)
            OutlinedButton(
                onClick = { navController.navigate("register") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                border = BorderStroke(2.dp, neonGreen),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = neonGreen
                )
            ) {
                Text(
                    "Registrarse",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }

        // 3. ENLACES DE PIE DE PÁGINA
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton(onClick = { navController.navigate("nosotros") }) {
                Text(
                    "Sobre Nosotros",
                    color = Color.White.copy(alpha = 0.6f),
                    fontFamily = FontFamily.Default,
                    fontSize = 14.sp
                )
            }
            TextButton(onClick = { navController.navigate("contacto") }) {
                Text(
                    "Contacto",
                    color = Color.White.copy(alpha = 0.6f),
                    fontFamily = FontFamily.Default,
                    fontSize = 14.sp
                )
            }
        }
    }
}