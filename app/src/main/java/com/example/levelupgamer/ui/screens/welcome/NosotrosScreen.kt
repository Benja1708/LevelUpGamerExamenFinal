package com.example.levelupgamer.ui.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NosotrosScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.background(Color.Black),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Sobre Nosotros",
                        color = Color(0xFF39FF14)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color(0xFF39FF14)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Black)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "LEVEL-UP GAMER",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF39FF14),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Tu Tienda Gaming en Chile",
                fontSize = 18.sp,
                color = Color(0xFF1E90FF),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "NUESTRA MISIÓN",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF39FF14),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        "Proporcionar productos de alta calidad para gamers en todo Chile, " +
                                "ofreciendo una experiencia de compra única y personalizada, con un " +
                                "enfoque en la satisfacción del cliente y el crecimiento de la comunidad gamer.",
                        fontSize = 14.sp,
                        color = Color.White,
                        lineHeight = 20.sp
                    )
                }
            }


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "NUESTRA VISIÓN",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E90FF),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        "Ser la tienda online líder en productos para gamers en Chile, " +
                                "reconocida por su innovación, servicio al cliente excepcional, " +
                                "y un programa de fidelización basado en gamificación que recompense " +
                                "a nuestros clientes más fieles.",
                        fontSize = 14.sp,
                        color = Color.White,
                        lineHeight = 20.sp
                    )
                }
            }


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "NUESTRA HISTORIA",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF39FF14),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        "Level-Up Gamer nació hace dos años como respuesta a la creciente " +
                                "demanda durante la pandemia. Aunque no contamos con una ubicación " +
                                "física, realizamos despachos a todo el país, llevando la mejor " +
                                "experiencia gaming directamente a tu hogar.",
                        fontSize = 14.sp,
                        color = Color.White,
                        lineHeight = 20.sp
                    )
                }
            }


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "NUESTROS VALORES",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E90FF),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    ValorItem(
                        icon = "🎮",
                        titulo = "Pasion Gaming",
                        descripcion = "Vivimos y respiramos gaming, entendemos lo que necesitas."
                    )

                    ValorItem(
                        icon = "⚡",
                        titulo = "Innovación Constante",
                        descripcion = "Siempre a la vanguardia con los últimos productos y tecnologías."
                    )

                    ValorItem(
                        icon = "🤝",
                        titulo = "Comunidad First",
                        descripcion = "Nuestra comunidad gamer es el corazón de todo lo que hacemos."
                    )

                    ValorItem(
                        icon = "🚀",
                        titulo = "Calidad Garantizada",
                        descripcion = "Solo productos originales de primeras marcas."
                    )
                }
            }


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "IMPACTO COMUNITARIO",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF39FF14),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        "Tus compras apoyan directamente a la comunidad gamer chilena. " +
                                "Organizamos y patrocinamos eventos locales, torneos y meetups " +
                                "para fortalecer nuestra comunidad.",
                        fontSize = 14.sp,
                        color = Color.White,
                        lineHeight = 20.sp
                    )
                }
            }

            // Mensaje final
            Text(
                text = "\"PCFactory nos COPIO\"",
                fontSize = 16.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                color = Color(0xFF39FF14),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun ValorItem(icon: String, titulo: String, descripcion: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = icon,
            fontSize = 24.sp,
            modifier = Modifier.padding(end = 12.dp)
        )
        Column {
            Text(
                text = titulo,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF39FF14),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = descripcion,
                fontSize = 14.sp,
                color = Color.White,
                lineHeight = 18.sp
            )
        }
    }
}