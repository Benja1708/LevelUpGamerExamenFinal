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
fun ProductoDetailScreen(navController: NavController, productoId: Int) {
    val viewModel: ProductoViewModel = viewModel()
    val productos by viewModel.productos.collectAsState()

    val producto = productos.find { it.id == productoId }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (producto == null) {
            Text(
                "Producto no encontrado",
                color = Color(0xFF39FF14),
                fontFamily = FontFamily.Default, // tipografía por defecto
                style = MaterialTheme.typography.headlineMedium
            )
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    producto.nombre,
                    style = MaterialTheme.typography.headlineLarge,
                    fontFamily = FontFamily.Default,
                    color = Color(0xFF39FF14)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    producto.descripcion,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily.Default,
                    color = Color(0xFF39FF14)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Precio: $${producto.precio}",
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Default,
                    color = Color(0xFF39FF14)
                )

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF39FF14),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver", fontFamily = FontFamily.Default)
                }
            }
        }
    }
}
