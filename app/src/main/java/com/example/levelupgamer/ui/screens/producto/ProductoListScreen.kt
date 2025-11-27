package com.example.levelupgamer.ui.screens.producto

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.R
import com.example.levelupgamer.data.model.Producto
import com.example.levelupgamer.util.toClp
import com.example.levelupgamer.viewmodel.CarritoViewModel
import com.example.levelupgamer.viewmodel.ProductoViewModel
import com.example.levelupgamer.viewmodel.UserViewModel
import kotlinx.coroutines.launch

private val categoriasCaso = listOf(
    "Todos",
    "Juegos de Mesa",
    "Accesorios",
    "Consolas",
    "Computadores Gamers",
    "Sillas Gamers",
    "Mouse",
    "Mousepad",
    "Poleras Personalizadas"
)
fun getProductCategory(producto: Producto): String {
    return when {
        producto.descripcion.contains("Juegos de Mesa", ignoreCase = true) -> "Juegos de Mesa"
        producto.descripcion.contains("Accesorios", ignoreCase = true) -> "Accesorios"
        producto.descripcion.contains("Consolas", ignoreCase = true) -> "Consolas"
        producto.descripcion.contains("Computadores Gamers", ignoreCase = true) -> "Computadores Gamers"
        producto.descripcion.contains("Sillas Gamers", ignoreCase = true) -> "Sillas Gamers"
        producto.descripcion.contains("Mousepad", ignoreCase = true) -> "Mousepad"
        (producto.descripcion.contains("Mouse", ignoreCase = true) || producto.nombre.contains("Mouse", ignoreCase = true)) && !producto.descripcion.contains("Mousepad", ignoreCase = true) -> "Mouse"
        producto.descripcion.contains("Poleras Personalizadas", ignoreCase = true) -> "Poleras Personalizadas"
        else -> "Otros"
    }
}
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
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }
    var searchQuery by remember { mutableStateOf("") }
    val productosFiltrados = remember(productos, categoriaSeleccionada, searchQuery) {
        productos
            .filter { prod ->
                val categoryMatch = categoriaSeleccionada == "Todos" || getProductCategory(prod) == categoriaSeleccionada
                val queryMatch = searchQuery.isBlank() ||
                        prod.nombre.contains(searchQuery, ignoreCase = true) ||
                        prod.descripcion.contains(searchQuery, ignoreCase = true)
                categoryMatch && queryMatch
            }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = Color(0xFF121212),

        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 70.dp)
            ) { data ->

                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1F1F1F)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = Color(0xFF39FF14)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Producto agregado",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = data.visuals.message,
                                color = Color(0xFFD3D3D3),
                                fontSize = 13.sp
                            )
                        }

                        if (data.visuals.actionLabel != null) {
                            TextButton(onClick = { data.performAction() }) {
                                Text(
                                    text = data.visuals.actionLabel!!,
                                    color = Color(0xFF39FF14),
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        },

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

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar productos...", color = Color.White.copy(alpha = 0.7f)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color(0xFF39FF14)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF39FF14),
                    unfocusedBorderColor = Color(0xFF2E2E2E),
                    focusedLabelColor = Color(0xFF39FF14),
                    cursorColor = Color(0xFF39FF14),
                    focusedContainerColor = Color(0xFF1F1F1F),
                    unfocusedContainerColor = Color(0xFF1F1F1F),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                textStyle = TextStyle(color = Color.White)
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categoriasCaso.size) { index ->
                    val cat = categoriasCaso[index]
                    val seleccionado = cat == categoriaSeleccionada

                    FilterChip(
                        selected = seleccionado,
                        onClick = { categoriaSeleccionada = cat },
                        label = {
                            Text(
                                text = cat,
                                color = if (seleccionado) Color.Black else Color.White,
                                fontSize = 12.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF39FF14),
                            containerColor = Color(0xFF2E2E2E)
                        )
                    )
                }
            }

            if (productosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay productos para esta categoría",
                        color = Color.White
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = productosFiltrados,
                        key = { it.id }
                    ) { prod ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(
                                animationSpec = tween(durationMillis = 350)
                            ) + slideInVertically(
                                animationSpec = tween(durationMillis = 350),
                                initialOffsetY = { it / 2 }
                            ),
                            exit = fadeOut(
                                animationSpec = tween(durationMillis = 200)
                            ) + slideOutVertically(
                                animationSpec = tween(durationMillis = 200),
                                targetOffsetY = { it / 2 }
                            )
                        ) {
                            ProductoCard(
                                producto = prod,
                                navController = navController,
                                isAdmin = isAdmin,
                                onEdit = { navController.navigate("editProducto/${prod.id}") },
                                onDelete = { productoViewModel.eliminarProducto(prod) },
                                onAddToCart = {
                                    carritoViewModel.agregarAlCarrito(prod)

                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "\"${prod.nombre}\" agregado al carrito",
                                            withDismissAction = true
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
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
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .clickable { navController.navigate("productoDetalle/${producto.id}") },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2E2E2E)
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(id = productoImage(producto.nombre)),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(135.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF333333))
                    .padding(horizontal = 8.dp, vertical = 6.dp)
                    .weight(1f)
            ) {

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = producto.nombre,
                        color = Color(0xFF39FF14),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 2,
                        lineHeight = 18.sp
                    )

                    Text(
                        text = "$${producto.precio.toClp()}",
                        color = Color(0xFF39FF14),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                if (isAdmin) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Button(
                            onClick = onEdit,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Yellow,
                                contentColor = Color.Black
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp)
                        ) { Text("Editar", fontSize = 12.sp) }

                        Button(
                            onClick = onDelete,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp)
                        ) { Text("Eliminar", fontSize = 12.sp) }
                    }
                } else {
                    Button(
                        onClick = onAddToCart,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6B5BFF),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(38.dp)
                    ) {
                        Text("Agregar al carrito", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

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
        else -> R.drawable.ic_launcher_background
    }
}