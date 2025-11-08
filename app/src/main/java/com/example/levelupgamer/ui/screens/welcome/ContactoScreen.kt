package com.example.levelupgamer.ui.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactoScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.background(Color.Black),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Contáctanos",
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
                text = "¿Necesitas Ayuda?",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF39FF14),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Estamos aquí para apoyarte en tu experiencia gaming",
                fontSize = 16.sp,
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
                        "INFORMACIÓN DE CONTACTO",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF39FF14),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    ContactoItem(
                        icon = Icons.Default.Phone,
                        titulo = "Teléfono de Soporte",
                        descripcion = "+56 9 1234 5678",
                        accion = "Llamar ahora"
                    )

                    ContactoItem(
                        icon = Icons.Default.Email,
                        titulo = "Correo Electrónico",
                        descripcion = "soporte@levelupgamer.cl",
                        accion = "Enviar email"
                    )

                    ContactoItem(
                        icon = Icons.Default.Call,
                        titulo = "WhatsApp",
                        descripcion = "+56 9 8765 4321",
                        accion = "Chatear por WhatsApp"
                    )

                    ContactoItem(
                        icon = Icons.Default.LocationOn,
                        titulo = "Despachos",
                        descripcion = "A todo Chile",
                        accion = "Ver cobertura"
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
                        "HORARIOS DE ATENCIÓN",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E90FF),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    HorarioItem(
                        dia = "Lunes a Viernes",
                        horario = "09:00 - 20:00 hrs"
                    )

                    HorarioItem(
                        dia = "Sábados",
                        horario = "10:00 - 18:00 hrs"
                    )

                    HorarioItem(
                        dia = "Domingos y Festivos",
                        horario = "12:00 - 16:00 hrs"
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
                        "SERVICIOS DISPONIBLES",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF39FF14),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    ServicioItem(
                        servicio = "Soporte Técnico",
                        descripcion = "Asistencia para productos y garantías"
                    )

                    ServicioItem(
                        servicio = "Consultas de Pedidos",
                        descripcion = "Seguimiento y estado de tus compras"
                    )

                    ServicioItem(
                        servicio = "Asesoría Gaming",
                        descripcion = "Recomendaciones personalizadas"
                    )

                    ServicioItem(
                        servicio = "Soporte Garantías",
                        descripcion = "Gestión de garantías y devoluciones"
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
                        "CONTACTO RÁPIDO",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E90FF),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )


                    Text(
                        "¿Tienes una consulta específica? Utiliza nuestros canales " +
                                "directos de WhatsApp para una respuesta inmediata. " +
                                "Nuestro equipo de soporte está listo para ayudarte.",
                        fontSize = 14.sp,
                        color = Color.White,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = {

                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF39FF14),
                            contentColor = Color.Black
                        )
                    ) {
                        Text("Contactar por WhatsApp", fontWeight = FontWeight.Bold)
                    }
                }
            }


            Text(
                text = "\"¡Cualquier duda consulte!\"",
                fontSize = 14.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                color = Color(0xFF39FF14),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 24.dp)
            )
        }
    }
}

@Composable
fun ContactoItem(icon: ImageVector, titulo: String, descripcion: String, accion: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF1E90FF),
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 12.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = titulo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF39FF14)
                )
                Text(
                    text = descripcion,
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = accion,
                    fontSize = 12.sp,
                    color = Color(0xFF1E90FF),
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }
    }
}

@Composable
fun HorarioItem(dia: String, horario: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = dia,
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = horario,
            fontSize = 14.sp,
            color = Color(0xFF39FF14),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ServicioItem(servicio: String, descripcion: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "•",
            fontSize = 16.sp,
            color = Color(0xFF1E90FF),
            modifier = Modifier.padding(end = 12.dp)
        )
        Column {
            Text(
                text = servicio,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF39FF14),
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = descripcion,
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}