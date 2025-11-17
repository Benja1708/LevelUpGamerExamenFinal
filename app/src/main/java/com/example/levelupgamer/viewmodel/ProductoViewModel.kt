package com.example.levelupgamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.levelupgamer.data.ProductoDatabase
import com.example.levelupgamer.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first

class ProductoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        ProductoDatabase::class.java,
        "levelupgamer.db"
    ).build()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        viewModelScope.launch {
            val dao = db.productoDao()

            val actuales = dao.getAllProductos().first()

            val examen = listOf(
                Producto(
                    nombre = "Catan",
                    descripcion = "JM001 • Juegos de Mesa • Catan",
                    precio = 29990.0
                ),
                Producto(
                    nombre = "Carcassonne",
                    descripcion = "JM002 • Juegos de Mesa • Carcassonne",
                    precio = 24990.0
                ),
                Producto(
                    nombre = "Controlador Inalámbrico Xbox Series X",
                    descripcion = "AC001 • Accesorios",
                    precio = 59990.0
                ),
                Producto(
                    nombre = "Auriculares Gamer HyperX Cloud II",
                    descripcion = "AC002 • Accesorios",
                    precio = 79990.0
                ),
                Producto(
                    nombre = "PlayStation 5",
                    descripcion = "CO001 • Consolas",
                    precio = 549990.0
                ),
                Producto(
                    nombre = "PC Gamer ASUS ROG Strix",
                    descripcion = "CG001 • Computadores Gamers",
                    precio = 1299990.0
                ),
                Producto(
                    nombre = "Silla Gamer Secretlab Titan",
                    descripcion = "SG001 • Sillas Gamers",
                    precio = 349990.0
                ),
                Producto(
                    nombre = "Mouse Gamer Logitech G502 HERO",
                    descripcion = "MS001 • Mouse",
                    precio = 49990.0
                ),
                Producto(
                    nombre = "Mousepad Razer Goliathus Extended",
                    descripcion = "MP001 • Mousepad",
                    precio = 29990.0
                ),
                Producto(
                    nombre = "Polera Gamer Personalizada \"Level-Up\"",
                    descripcion = "PP001 • Poleras Personalizadas",
                    precio = 14990.0
                )
            )

            val nombresActuales = actuales.map { it.nombre }.toSet()
            examen.forEach { prod ->
                if (prod.nombre !in nombresActuales) {
                    dao.insert(prod)
                }
            }

            dao.getAllProductos().collect { lista ->
                _productos.value = lista
            }
        }
    }

    fun getProducto(id: Int) = db.productoDao().getProductoById(id)

    fun agregarProducto(nombre: String, descripcion: String, precio: Double) {
        viewModelScope.launch {
            db.productoDao().insert(
                Producto(
                    nombre = nombre,
                    descripcion = descripcion,
                    precio = precio
                )
            )
        }
    }

    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch {
            db.productoDao().update(producto)
        }
    }

    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            db.productoDao().delete(producto)
        }
    }
}

