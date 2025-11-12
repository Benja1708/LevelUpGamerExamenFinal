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
import com.example.levelupgamer.viewmodel.UserViewModel
import com.example.levelupgamer.ui.screens.resenias.ReviewSection

@Composable
fun ProductoDetailScreen(
    navController: NavController,
    productoId: Int
) {
    val productoViewModel: ProductoViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel() // 👈 para obtener el nombre del usuario
    val productos by productoViewModel.productos.collectAsState()
    val currentUser by userViewModel.currentUser.collectAsState()

    val producto = productos.find { it.id == productoId }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        if (producto == null) {
            Text(
                "Producto no encontrado",
                color = Color(0xFF39FF14),
                fontFamily = FontFamily.Default,
                style = MaterialTheme.typography.headlineMedium
            )
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ---------- Detalle del producto ----------
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

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color(0x22FFFFFF))
                Spacer(modifier = Modifier.height(8.dp))

                // ---------- Sección de Reseñas ----------
                // Pinta lista de reseñas, promedio y formulario para agregar
                ReviewSection(
                    productId = productoId,
                    currentUserName = currentUser?.nombre
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
