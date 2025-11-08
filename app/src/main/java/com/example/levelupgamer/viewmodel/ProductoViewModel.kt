package com.example.levelupgamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.levelupgamer.data.ProductoDatabase
import com.example.levelupgamer.data.model.Producto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        ProductoDatabase::class.java, "levelupgamer.db"
    ).build()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        viewModelScope.launch {
            db.productoDao().getAllProductos().collect { lista ->
                _productos.value = lista
            }
        }
    }

    fun getProducto(id: Int): Flow<Producto> {
        return db.productoDao().getProductoById(id)
    }

    fun agregarProducto(nombre: String, descripcion: String, precio: Double) {
        viewModelScope.launch {
            db.productoDao().insert(Producto(nombre = nombre, descripcion = descripcion, precio = precio))
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
