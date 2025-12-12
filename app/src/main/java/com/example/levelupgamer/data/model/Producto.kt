package com.example.levelupgamer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "producto")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val codigo: String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val precio: Double = 0.0,
    val categoria: String = "",
    val imagenRes: Int = 0,
    val stock: Int = 0
)
