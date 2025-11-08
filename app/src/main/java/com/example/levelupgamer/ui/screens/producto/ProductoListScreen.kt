package com.example.levelupgamer.ui.screens.producto

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.R
import com.example.levelupgamer.data.model.Producto
import com.example.levelupgamer.viewmodel.CarritoViewModel
import com.example.levelupgamer.viewmodel.ProductoViewModel
import com.example.levelupgamer.viewmodel.UserViewModel

@Composable
fun ProductoListScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    productoViewModel: ProductoViewModel = viewModel(),
    carritoViewModel: CarritoViewModel = viewModel()
) {
    val productos by productoViewModel.productos.collectAsState()
    val currentUser by userViewModel.currentUser.collectAsState()
    val isAdmin = currentUser?.correo?.endsWith("@admin.cl") == true

    Scaffold(
        containerColor = Color(0xFF121212),
        floatingActionButton = {
            if (isAdmin) {
                FloatingActionButton(
                    onClick = { navController.navigate("agregarProducto") },
                    containerColor = Color(0xFF39FF14),
                    contentColor = Color.Black
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Agregar Producto")
                }
            }
        }
    ) { padding ->
        androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
            columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2),
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(productos.size) { index ->
                val prod = productos[index]
                ProductoCard(
                    producto = prod,
                    navController = navController,
                    isAdmin = isAdmin,
                    onEdit = { navController.navigate("editProducto/${prod.id}") },
                    onDelete = { productoViewModel.eliminarProducto(prod) },
                    onAddToCart = { carritoViewModel.agregarAlCarrito(prod) }
                )
            }
        }
    }
}

@Composable
fun ProductoCard(
    producto: Producto,
    navController: NavController,
    isAdmin: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onAddToCart: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)              // 👈 mismo alto para todos
            .clickable { navController.navigate("productoDetalle/${producto.id}") },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2E2E2E)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // imagen arriba
            Image(
                painter = painterResource(id = productoImage(producto.nombre)),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                contentScale = ContentScale.Crop
            )

            // info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF555555))
                    .padding(8.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = producto.nombre,
                        color = Color(0xFF39FF14),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 2
                    )
                    Text(
                        text = "$${producto.precio}",
                        color = Color(0xFF39FF14),
                        fontSize = 12.sp
                    )
                }

                if (isAdmin) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = onEdit,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Editar", color = Color.Black, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Button(
                            onClick = onDelete,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Eliminar", color = Color.White, fontSize = 12.sp)
                        }
                    }
                } else {
                    Button(
                        onClick = onAddToCart,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B5BFF)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Agregar al carrito", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

/**
 * Mapea el nombre del producto a una imagen local.
 * Si no encuentra, usa una por defecto.
 */
private fun productoImage(nombre: String): Int {
    return when {
        nombre.contains("catan", ignoreCase = true) -> R.drawable.catan
        nombre.contains("carcassonne", ignoreCase = true) -> R.drawable.carcassonne
        nombre.contains("xbox", ignoreCase = true) -> R.drawable.control_xbox
        nombre.contains("hyperx", ignoreCase = true) -> R.drawable.audifonos_hyperx
        nombre.contains("playstation", ignoreCase = true) || nombre.contains("ps5", ignoreCase = true) -> R.drawable.ps5
        nombre.contains("pc", ignoreCase = true) && nombre.contains("gamer", ignoreCase = true) -> R.drawable.pc_gamer_asus
        nombre.contains("silla", ignoreCase = true) -> R.drawable.silla_gamer
        nombre.contains("mousepad", ignoreCase = true) -> R.drawable.mousepad_gamer
        nombre.contains("mouse", ignoreCase = true) -> R.drawable.mouse_gamer
        nombre.contains("polera", ignoreCase = true) -> R.drawable.polera_gamer
        else -> R.drawable.ic_launcher_background   // 👈 fallback
    }
}
