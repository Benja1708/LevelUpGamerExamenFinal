package com.example.levelupgamer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "producto")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // 👇 nuevo campo que pide la evaluación
    val codigo: String = "",

    val nombre: String = "",
    val descripcion: String = "",
    val precio: Double = 0.0,

    // 👇 otro que pide la evaluación
    val categoria: String = "",

    // para que puedas mostrar la foto que pusiste en drawable
    val imagenRes: Int = 0
)
