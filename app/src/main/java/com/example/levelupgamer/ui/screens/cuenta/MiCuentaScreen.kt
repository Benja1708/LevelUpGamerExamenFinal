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

    Scaffold(
        modifier = Modifier.background(Color.Black),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mi Cuenta",
                        color = Color(0xFF39FF14),
                        fontFamily = FontFamily.Default
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
                actions = {
                    if (currentUser != null && !isEditing) {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Editar",
                                tint = Color(0xFF1E90FF)
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
                        tint = Color(0xFF39FF14),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No has iniciado sesión",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF39FF14)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Inicia sesión para ver tu cuenta",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFD3D3D3)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            navController.navigate("welcome") {
                                popUpTo("miCuenta") { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF39FF14),
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
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Usuario",
                                tint = Color(0xFF39FF14),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Bienvenido, ${user.nombre}",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF39FF14),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Level-Up Gamer",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF1E90FF)
                            )
                        }
                    }

                    mensaje?.let {
                        Text(
                            text = it,
                            color = if (it.contains("éxito") || it.contains("correctamente")) Color(0xFF39FF14) else Color.Red,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    if (isEditing) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "Editar Información",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFF39FF14),
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )

                                OutlinedTextField(
                                    value = editedNombre,
                                    onValueChange = { editedNombre = it },
                                    label = { Text("Nombre", color = Color(0xFF39FF14)) },
                                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                OutlinedTextField(
                                    value = editedCorreo,
                                    onValueChange = { editedCorreo = it },
                                    label = { Text("Correo", color = Color(0xFF39FF14)) },
                                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                OutlinedTextField(
                                    value = editedContrasena,
                                    onValueChange = { editedContrasena = it },
                                    label = { Text("Nueva Contraseña (opcional)", color = Color(0xFF39FF14)) },
                                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                                    visualTransformation = PasswordVisualTransformation(),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                OutlinedTextField(
                                    value = editedAnioNacimiento,
                                    onValueChange = { editedAnioNacimiento = it },
                                    label = { Text("Año de Nacimiento", color = Color(0xFF39FF14)) },
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
                                                    userViewModel.updateUser(updatedUser) { success, errorMessage ->
                                                        if (success) {
                                                            mensaje = "Datos actualizados correctamente"
                                                            isEditing = false
                                                            editedContrasena = ""
                                                        } else {
                                                            mensaje = errorMessage ?: "Error al actualizar los datos"
                                                        }
                                                    }
                                                }
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF39FF14),
                                            contentColor = Color.Black
                                        )
                                    ) {
                                        Text("Guardar Cambios")
                                    }
                                }
                            }
                        }
                    } else {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                InfoRow("Nombre:", user.nombre, Color(0xFF39FF14))
                                InfoRow("Correo:", user.correo, Color(0xFF1E90FF))
                                InfoRow("Año de Nacimiento:", user.anioNacimiento.toString(), Color(0xFF39FF14))
                                InfoRow("Edad:", calcularEdad(user.anioNacimiento).toString() + " años", Color(0xFF1E90FF))

                                Spacer(modifier = Modifier.height(12.dp))

                                // 👇 aquí va el programa de referidos/gamificación
                                InfoRow("Puntos LevelUp:", puntos.toString(), Color(0xFF39FF14))
                                InfoRow("Nivel:", "Lv. $nivelCalculado", Color(0xFF39FF14))
                                InfoRow("Tu código:", codigo, Color(0xFF1E90FF))

                                Spacer(modifier = Modifier.height(16.dp))

                                if (user.correo.endsWith("@duocuc.cl")) {
                                    Text(
                                        "🎓 Beneficio DuocUC: 20% de descuento permanente",
                                        color = Color(0xFF39FF14),
                                        fontWeight = FontWeight.Bold
                                    )
                                } else {
                                    Text(
                                        "👤 Cuenta estándar",
                                        color = Color(0xFF1E90FF),
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {
                                userViewModel.logout()
                                carritoViewModel.limpiarCarrito()
                                navController.popBackStack()
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
