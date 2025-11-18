package com.example.levelupgamer.ui.screens.news

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupgamer.data.model.NewsItem
import com.example.levelupgamer.viewmodel.NewsViewModel

private val filtros = listOf("Todos", "Noticias", "Eventos")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel = viewModel()
) {
    val items by newsViewModel.items.collectAsState()
    val filtro by newsViewModel.filtro.collectAsState()

    val itemsFiltrados = remember(items, filtro) {
        when (filtro) {
            "Noticias" -> items.filter { it.tipo == "Noticia" }
            "Eventos"  -> items.filter { it.tipo == "Evento" }
            else       -> items
        }
    }

    Scaffold(
        containerColor = Color(0xFF121212),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Noticias & Eventos",
                        color = Color(0xFF39FF14),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFF121212))
        ) {
            // Filtros (chips)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filtros.forEach { f ->
                    val selected = f == filtro
                    FilterChip(
                        selected = selected,
                        onClick = { newsViewModel.cambiarFiltro(f) },
                        label = {
                            Text(
                                text = f,
                                color = if (selected) Color.Black else Color.White,
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

            if (itemsFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay noticias ni eventos aún",
                        color = Color.LightGray
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(itemsFiltrados) { item ->
                        NewsCard(item)
                    }
                }
            }
        }
    }
}

@Composable
private fun NewsCard(item: NewsItem) {
    var expandido by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Título + tipo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.titulo,
                    color = Color(0xFF39FF14),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.tipo.uppercase(),
                    color = if (item.tipo == "Evento") Color(0xFF1E90FF) else Color(0xFFFFC107),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.fecha,
                color = Color.Gray,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.resumen,
                color = Color.White,
                fontSize = 13.sp
            )

            AnimatedVisibility(visible = expandido) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color(0xFF333333))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.contenido,
                        color = Color(0xFFE0E0E0),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { expandido = !expandido },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = if (expandido) "Ver menos" else "Ver más",
                    color = Color(0xFF39FF14),
                    fontSize = 13.sp
                )
            }
        }
    }
}