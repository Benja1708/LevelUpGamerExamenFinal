package com.example.levelupgamer.ui.screens.producto


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.viewmodel.ProductoViewModel

@Composable
fun AgregarProductoScreen(navController: NavController, vm: ProductoViewModel = viewModel()) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Agregar Producto",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color(0xFF39FF14),
                fontFamily = FontFamily.Default
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre", color = Color(0xFF39FF14)) },
            textStyle = LocalTextStyle.current.copy(color = Color(0xFF39FF14)),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción", color = Color(0xFF39FF14)) },
            textStyle = LocalTextStyle.current.copy(color = Color(0xFF39FF14)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio", color = Color(0xFF39FF14)) },
            textStyle = LocalTextStyle.current.copy(color = Color(0xFF39FF14)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        if (error.isNotEmpty()) {
            Text(error, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val precioDouble = precio.toDoubleOrNull()
                if (nombre.isBlank() || descripcion.isBlank() || precioDouble == null) {
                    error = "Completa todos los campos correctamente"
                    return@Button
                }
                vm.agregarProducto(nombre, descripcion, precioDouble)
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF39FF14), contentColor = Color.Black),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar")
        }
    }
}
