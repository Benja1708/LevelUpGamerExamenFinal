package com.example.levelupgamer.ui.screens.news

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.data.model.NewsItem
import com.example.levelupgamer.ui.theme.Green80
import com.example.levelupgamer.viewmodel.NewsViewModel
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import android.util.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    navController: NavController,
    viewModel: NewsViewModel = viewModel()
) {
    val newsItems by viewModel.items.collectAsState()
    val filtro by viewModel.filtro.collectAsState()

    val filteredItems = if (filtro == "Todos") {
        newsItems
    } else {
        newsItems.filter { it.tipo == filtro }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("LevelUp Noticias") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            FiltroSection(
                filtroActual = filtro,
                onFiltroChange = viewModel::cambiarFiltro
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filteredItems) { item ->
                    NewsItemCard(item, navController)
                }
            }
        }
    }
}

@Composable
fun FiltroSection(
    filtroActual: String,
    onFiltroChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Chip(
            texto = "Todos",
            isSelected = filtroActual == "Todos",
            onClick = { onFiltroChange("Todos") }
        )
        Chip(
            texto = "Noticia",
            isSelected = filtroActual == "Noticia",
            onClick = { onFiltroChange("Noticia") }
        )
        Chip(
            texto = "Evento",
            isSelected = filtroActual == "Evento",
            onClick = { onFiltroChange("Evento") }
        )
    }
}

@Composable
fun Chip(
    texto: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isSelected) Green80.copy(alpha = 0.5f) else Color.Transparent,
            contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(1.dp, Green80)
    ) {
        Text(texto)
    }
}

@Composable
fun NewsItemCard(item: NewsItem, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = item.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.tipo,
                        style = MaterialTheme.typography.labelSmall,
                        color = Green80,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = item.fecha,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.titulo,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.resumen,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("newsDetail/${item.id}")
                        }
                        .padding(vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Detalles",
                        tint = Green80,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Leer más",
                        color = Green80,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}