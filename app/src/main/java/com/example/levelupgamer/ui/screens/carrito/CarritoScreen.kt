package com.example.levelupgamer.ui.screens.carrito

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.util.toClp
import com.example.levelupgamer.viewmodel.CarritoViewModel
import com.example.levelupgamer.viewmodel.UserViewModel
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(navController: NavController, userViewModel: UserViewModel) {
    val carritoViewModel: CarritoViewModel = viewModel()
    val productosCarrito by carritoViewModel.productosCarrito.collectAsState()
    val totalCarrito by carritoViewModel.totalCarrito.collectAsState()
    val currentUser by userViewModel.currentUser.collectAsState()
    val tieneDescuento = currentUser?.correo?.endsWith("@duocuc.cl") == true
    val totalConDescuento = if (tieneDescuento) totalCarrito * 0.8 else totalCarrito

    Scaffold(
        modifier = Modifier.background(Color.Black),
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mi Carrito",
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        },
        bottomBar = {
            if (productosCarrito.isNotEmpty()) {
                val totalFormateado = totalCarrito.toClp()
                val totalConDescFormateado = totalConDescuento.toClp()

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = Color.DarkGray,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        if (tieneDescuento) {
                            Text(
                                "¡Descuento del 20% aplicado!",
                                color = Color(0xFF39FF14),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Total sin descuento:",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFFD3D3D3)
                                )
                                Text(
                                    "$$totalFormateado",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFFD3D3D3),
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                if (tieneDescuento) "Total a pagar:" else "Total:",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF39FF14),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "$$totalConDescFormateado",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF39FF14),
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                navController.navigate("checkout")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF39FF14),
                                contentColor = Color.Black
                            )
                        ) {
                            Text("Finalizar Compra", fontWeight = FontWeight.Bold)
                        }

                        TextButton(
                            onClick = { carritoViewModel.limpiarCarrito() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Limpiar Carrito",
                                color = Color(0xFF1E90FF)
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        if (productosCarrito.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.Black)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Carrito vacío",
                    tint = Color(0xFF39FF14),
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Tu carrito está vacío",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF39FF14)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Agrega algunos productos desde la lista",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFD3D3D3)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.Black)
                    .padding(16.dp)
            ) {
                items(productosCarrito) { carritoItem ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = carritoItem.imageResId),
                                contentDescription = carritoItem.nombre,
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(end = 12.dp),
                                contentScale = ContentScale.Crop
                            )

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    carritoItem.nombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFF39FF14),
                                    fontFamily = FontFamily.Default
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    carritoItem.descripcion,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFFD3D3D3),
                                    fontFamily = FontFamily.Default
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "$${carritoItem.precio.toClp()} x ${carritoItem.cantidad}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color(0xFF1E90FF),
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "Subtotal: $${(carritoItem.precio * carritoItem.cantidad).toClp()}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF39FF14)
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                IconButton(
                                    onClick = {
                                        carritoViewModel.actualizarCantidad(carritoItem, carritoItem.cantidad + 1)
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = "Aumentar",
                                        tint = Color(0xFF39FF14)
                                    )
                                }
                                Text(
                                    carritoItem.cantidad.toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                IconButton(
                                    onClick = {
                                        carritoViewModel.actualizarCantidad(carritoItem, carritoItem.cantidad - 1)
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Disminuir",
                                        tint = Color(0xFF1E90FF)
                                    )
                                }
                            }

                            IconButton(
                                onClick = { carritoViewModel.eliminarDelCarrito(carritoItem) }
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}