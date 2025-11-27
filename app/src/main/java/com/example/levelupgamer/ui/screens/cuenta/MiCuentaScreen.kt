package com.example.levelupgamer.ui.screens.cuenta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.viewmodel.CarritoViewModel
import com.example.levelupgamer.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiCuentaScreen(navController: NavController, userViewModel: UserViewModel) {
    val currentUser by userViewModel.currentUser.collectAsState()
    val carritoViewModel: CarritoViewModel = viewModel()

    var isEditing by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf<String?>(null) }

    var editedNombre by remember { mutableStateOf("") }
    var editedCorreo by remember { mutableStateOf("") }
    var editedContrasena by remember { mutableStateOf("") }
    var editedAnioNacimiento by remember { mutableStateOf("") }

    LaunchedEffect(currentUser) {
        currentUser?.let {
            editedNombre = it.nombre
            editedCorreo = it.correo
            editedAnioNacimiento = it.anioNacimiento.toString()
        }
    }

    // Colores de acento
    val neonGreen = Color(0xFF39FF14)
    val neonBlue = Color(0xFF1E90FF)
    val darkGray = Color.DarkGray
    val lightGray = Color(0xFFD3D3D3)

    Scaffold(
        modifier = Modifier.background(Color.Black),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mi Cuenta",
                        color = neonGreen,
                        fontFamily = FontFamily.Default
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = neonGreen
                        )
                    }
                },
                actions = {
                    if (currentUser != null && !isEditing) {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Editar",
                                tint = neonBlue
                            )
                        }
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
        ) {
            if (currentUser == null) {
                // Bloque: No has iniciado sesión
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "No logueado",
                        tint = neonGreen,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No has iniciado sesión",
                        style = MaterialTheme.typography.titleMedium,
                        color = neonGreen
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Inicia sesión para ver tu cuenta",
                        style = MaterialTheme.typography.bodyMedium,
                        color = lightGray
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            navController.navigate("login") {
                                popUpTo("welcome") { inclusive = false }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = neonGreen,
                            contentColor = Color.Black
                        )
                    ) {
                        Text("Iniciar Sesión")
                    }
                }
            } else {
                val user = currentUser!!
                val puntos = user.puntos ?: 0
                val nivelCalculado = (puntos / 50) + 1
                val codigo = user.referralCode?.takeIf { it.isNotBlank() } ?: "-"

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Card de Bienvenida
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = darkGray)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Usuario",
                                tint = neonGreen,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Bienvenido, ${user.nombre}",
                                style = MaterialTheme.typography.titleMedium,
                                color = neonGreen,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Level-Up Gamer",
                                style = MaterialTheme.typography.bodyMedium,
                                color = neonBlue
                            )
                        }
                    }

                    mensaje?.let {
                        Text(
                            text = it,
                            color = if (it.contains("éxito") || it.contains("correctamente")) neonGreen else Color.Red,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    // Bloque de Edición
                    if (isEditing) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(containerColor = darkGray)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "Editar Información",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = neonGreen,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )

                                OutlinedTextField(
                                    value = editedNombre,
                                    onValueChange = { editedNombre = it },
                                    label = { Text("Nombre", color = neonGreen) },
                                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                OutlinedTextField(
                                    value = editedCorreo,
                                    onValueChange = { editedCorreo = it },
                                    label = { Text("Correo", color = neonGreen) },
                                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                OutlinedTextField(
                                    value = editedContrasena,
                                    onValueChange = { editedContrasena = it },
                                    label = { Text("Nueva Contraseña (opcional)", color = neonGreen) },
                                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                                    visualTransformation = PasswordVisualTransformation(),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                OutlinedTextField(
                                    value = editedAnioNacimiento,
                                    onValueChange = { editedAnioNacimiento = it },
                                    label = { Text("Año de Nacimiento", color = neonGreen) },
                                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Button(
                                        onClick = {
                                            isEditing = false
                                            editedNombre = user.nombre
                                            editedCorreo = user.correo
                                            editedContrasena = ""
                                            editedAnioNacimiento = user.anioNacimiento.toString()
                                            mensaje = null
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Gray
                                        )
                                    ) {
                                        Text("Cancelar", color = Color.White)
                                    }

                                    Button(
                                        onClick = {
                                            val correoValido = editedCorreo.endsWith("@duocuc.cl") || editedCorreo.endsWith("@gmail.com") || editedCorreo.endsWith("@admin.cl")
                                            val anioValido = editedAnioNacimiento.toIntOrNull()?.let { it <= 2005 } ?: false

                                            when {
                                                editedNombre.isBlank() || editedCorreo.isBlank() || editedAnioNacimiento.isBlank() -> {
                                                    mensaje = "Todos los campos son obligatorios"
                                                }
                                                !correoValido -> {
                                                    mensaje = "El correo debe terminar en @duocuc.cl, @gmail.com o @admin.cl"
                                                }
                                                !anioValido -> {
                                                    mensaje = "Debes ser mayor de 18 años"
                                                }
                                                else -> {
                                                    val updatedUser = user.copy(
                                                        nombre = editedNombre,
                                                        correo = editedCorreo,
                                                        contrasena = if (editedContrasena.isNotBlank()) editedContrasena else user.contrasena,
                                                        anioNacimiento = editedAnioNacimiento.toInt()
                                                    )

                                                    // FIX CLAVE: Llamada explícita a la función con los parámetros
                                                    userViewModel.updateUser(
                                                        user = updatedUser,
                                                        onComplete = { success, errorMessage ->
                                                            if (success) {
                                                                mensaje = "Datos actualizados correctamente"
                                                                isEditing = false
                                                                editedContrasena = ""
                                                            } else {
                                                                mensaje = errorMessage ?: "Error al actualizar los datos"
                                                            }
                                                        }
                                                    )
                                                }
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = neonGreen,
                                            contentColor = Color.Black
                                        )
                                    ) {
                                        Text("Guardar Cambios")
                                    }
                                }
                            }
                        }
                    } else {
                        // Bloque de Visualización (cuando no está editando)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(containerColor = darkGray)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                InfoRow("Nombre:", user.nombre, neonGreen)
                                InfoRow("Correo:", user.correo, neonBlue)
                                InfoRow("Año de Nacimiento:", user.anioNacimiento.toString(), neonGreen)
                                InfoRow("Edad:", calcularEdad(user.anioNacimiento).toString() + " años", neonBlue)

                                Spacer(modifier = Modifier.height(12.dp))

                                // 👇 aquí va el programa de referidos/gamificación
                                InfoRow("Puntos LevelUp:", puntos.toString(), neonGreen)
                                InfoRow("Nivel:", "Lv. $nivelCalculado", neonGreen)
                                InfoRow("Tu código:", codigo, neonBlue)

                                Spacer(modifier = Modifier.height(16.dp))

                                if (user.correo.endsWith("@duocuc.cl")) {
                                    Text(
                                        "🎓 Beneficio DuocUC: 20% de descuento permanente",
                                        color = neonGreen,
                                        fontWeight = FontWeight.Bold
                                    )
                                } else {
                                    Text(
                                        "👤 Cuenta estándar",
                                        color = neonBlue,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {
                                // FIX: Llama a logout() sin navController
                                userViewModel.logout()

                                // FIX: Maneja la navegación directamente en la pantalla
                                carritoViewModel.limpiarCarrito()
                                navController.popBackStack()
                                navController.navigate("welcome") {
                                    popUpTo("welcome") { inclusive = true }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Cerrar Sesión")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(etiqueta: String, valor: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = etiqueta,
            color = Color(0xFFD3D3D3),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = valor,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

fun calcularEdad(anioNacimiento: Int): Int {
    return java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) - anioNacimiento
}