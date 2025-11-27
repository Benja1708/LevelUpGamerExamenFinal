package com.example.levelupgamer.ui.screens.carrito

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelupgamer.util.toClp
import com.example.levelupgamer.viewmodel.CarritoViewModel
import com.example.levelupgamer.viewmodel.UserViewModel

@Composable
fun CheckoutScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel,
    userViewModel: UserViewModel
) {
    val productos by carritoViewModel.productosCarrito.collectAsState()
    val total = productos.sumOf { it.precio * it.cantidad }
    val currentUser by userViewModel.currentUser.collectAsState()
    var nombre by remember { mutableStateOf(currentUser?.nombre ?: "") }
    var direccion by remember { mutableStateOf("") }
    var metodoPago by remember { mutableStateOf("") }
    var mostrarDialogo by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Resumen del Pedido: $${total.toClp()}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF39FF14)
        )

        Divider(modifier = Modifier.padding(bottom = 8.dp))

        Text("1. Datos de Envío", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre Completo") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = currentUser?.correo ?: "",
            onValueChange = {},
            label = { Text("Email") },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección de Envío") },
            leadingIcon = { Icon(Icons.Default.Home, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text("2. Método de Pago", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = metodoPago,
            onValueChange = { metodoPago = it },
            label = { Text("N° Tarjeta o Pago") },
            leadingIcon = { Icon(Icons.Default.Payment, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank() && direccion.isNotBlank() && metodoPago.isNotBlank()) {
                    mostrarDialogo = true
                } else {
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF39FF14), contentColor = Color.Black)
        ) {
            Text("Confirmar Pedido y Pagar", fontWeight = FontWeight.Bold)
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = {
            },
            title = { Text("¡Compra Finalizada!") },
            text = { Text("Tu pedido por $${total.toClp()} ha sido procesado con éxito. ¡Gracias por tu compra!") },
            confirmButton = {
                Button(onClick = {
                    mostrarDialogo = false
                    carritoViewModel.vaciarCarrito()
                    navController.popBackStack("home", inclusive = false)
                }) {
                    Text("Aceptar")
                }
            }
        )
    }
}