package com.example.levelupgamer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carrito")
data class CarritoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productoId: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val cantidad: Int = 1,
    val imageResId: Int = 0
)