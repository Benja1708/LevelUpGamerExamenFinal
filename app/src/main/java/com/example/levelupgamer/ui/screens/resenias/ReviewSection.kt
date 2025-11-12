package com.example.levelupgamer.ui.screens.resenias

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupgamer.viewmodel.ReviewViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReviewSection(
    productId: Int,
    currentUserName: String?,
    reviewViewModel: ReviewViewModel = viewModel()
) {
    LaunchedEffect(productId) { reviewViewModel.setProduct(productId) }

    val reviews by reviewViewModel.reviews.collectAsState()
    val average by reviewViewModel.average.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {

        // Encabezado con promedio
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Reseñas",
                color = Color(0xFF39FF14),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.width(12.dp))
            RatingStars(rating = (average ?: 0.0))
            Spacer(Modifier.width(8.dp))
            Text(
                text = if (average != null) String.format(Locale.getDefault(), "%.1f", average) else "–",
                color = Color(0xFFD3D3D3)
            )
        }

        // Lista
        if (reviews.isEmpty()) {
            Text("Aún no hay reseñas", color = Color(0xFFD3D3D3))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 260.dp)
            ) {
                items(reviews) { r ->
                    ReviewItem(
                        userName = r.userName,
                        rating = r.rating,
                        comment = r.comment,
                        date = Date(r.createdAt)
                    )
                    Divider(color = Color(0x22FFFFFF))
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Formulario para agregar reseña
        var localRating by remember { mutableStateOf(5) }
        var comment by remember { mutableStateOf("") }

        Text("Escribir reseña", color = Color(0xFF39FF14), fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(8.dp))

        RatingSelector(
            rating = localRating,
            onChange = { localRating = it }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            label = { Text("Comentario", color = Color(0xFF39FF14)) },
            maxLines = 3
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (comment.isNotBlank()) {
                    reviewViewModel.addReview(
                        rating = localRating,
                        comment = comment,
                        userName = currentUserName ?: "Anónimo"
                    )
                    comment = ""
                    localRating = 5
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF39FF14),
                contentColor = Color.Black
            )
        ) { Text("Enviar reseña") }
    }
}

@Composable
private fun RatingStars(rating: Double, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        val full = rating.toInt()
        val empty = 5 - full
        repeat(full) { Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFD54F)) }
        repeat(empty) { Icon(Icons.Filled.StarBorder, contentDescription = null, tint = Color(0xFFFFD54F)) }
    }
}

@Composable
private fun RatingSelector(rating: Int, onChange: (Int) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        for (i in 1..5) {
            IconButton(onClick = { onChange(i) }) {
                Icon(
                    imageVector = if (i <= rating) Icons.Filled.Star else Icons.Filled.StarBorder,
                    contentDescription = "star $i",
                    tint = Color(0xFFFFD54F)
                )
            }
        }
    }
}

@Composable
private fun ReviewItem(userName: String, rating: Int, comment: String, date: Date) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(userName, color = Color.White, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.width(8.dp))
            RatingStars(rating.toDouble())
            Spacer(Modifier.width(8.dp))
            Text(
                SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(date),
                color = Color(0xFFAAAAAA),
                style = MaterialTheme.typography.bodySmall
            )
        }
        if (comment.isNotBlank()) {
            Spacer(Modifier.height(2.dp))
            Text(comment, color = Color(0xFFD3D3D3))
        }

    }
}