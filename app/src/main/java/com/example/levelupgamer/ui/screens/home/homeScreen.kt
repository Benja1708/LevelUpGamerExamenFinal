package com.example.levelupgamer.ui.screens.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelupgamer.R
import com.example.levelupgamer.viewmodel.UserViewModel
import androidx.compose.foundation.Image

@Composable
fun HomeScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val currentUser by userViewModel.currentUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "¡Bienvenido a LevelUpGamer!",
            color = Color(0xFF39FF14),
            fontFamily = FontFamily.Default,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = currentUser?.nombre?.let { "Hola, $it 👋" } ?: "Tu tienda gamer favorita",
            color = Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        val infiniteTransition = rememberInfiniteTransition()
        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.14f,
            animationSpec = infiniteRepeatable(
                animation = tween(1200, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo LevelUpGamer",
            modifier = Modifier
                .size(150.dp)
                .scale(scale)
        )

        Spacer(modifier = Modifier.height(28.dp))

        QuickActionButton("Ver productos") {
            navController.navigate("productos")
        }
        Spacer(modifier = Modifier.height(10.dp))

        QuickActionButton("Mi cuenta") {
            navController.navigate("miCuenta")
        }
        Spacer(modifier = Modifier.height(10.dp))

        QuickActionButton("Escanear QR") {
            navController.navigate("scanner")
        }
        Spacer(modifier = Modifier.height(10.dp))

        QuickActionButton("Noticias & Eventos") {
            navController.navigate("news")
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Destacados",
            color = Color(0xFF39FF14),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 4.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DestacadoCard("Catan", "$29.990", modifier = Modifier.weight(1f))
            DestacadoCard("Control Xbox", "$59.990", modifier = Modifier.weight(1f))
            DestacadoCard("PC Gamer", "$1.299.990", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun QuickActionButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .background(Color(0xFF39FF14), RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun DestacadoCard(
    titulo: String,
    precio: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(110.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(titulo, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text(precio, color = Color(0xFF39FF14), fontSize = 13.sp)
            Text("Ver más", color = Color.Gray, fontSize = 11.sp)
        }
    }
}
