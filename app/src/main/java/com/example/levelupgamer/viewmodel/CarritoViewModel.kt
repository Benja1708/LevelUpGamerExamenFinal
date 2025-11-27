package com.example.levelupgamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.R
import com.example.levelupgamer.data.database.CarritoDatabase
import com.example.levelupgamer.data.model.CarritoItem
import com.example.levelupgamer.data.model.Producto
import com.example.levelupgamer.data.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CarritoViewModel(application: Application) : AndroidViewModel(application) {

    private val carritoDao = CarritoDatabase.getDatabase(application).carritoDao()
    private val carritoRepository = CarritoRepository(carritoDao)

    private val _productosCarrito = MutableStateFlow<List<CarritoItem>>(emptyList())
    val productosCarrito: StateFlow<List<CarritoItem>> = _productosCarrito.asStateFlow()

    private val _totalCarrito = MutableStateFlow(0.0)
    val totalCarrito: StateFlow<Double> = _totalCarrito.asStateFlow()

    init {
        viewModelScope.launch {
            carritoRepository.getAllCarritoItems().collect { carritoItems ->
                _productosCarrito.value = carritoItems
                calcularTotal()
            }
        }
    }

    fun agregarAlCarrito(producto: Producto) {
        viewModelScope.launch {
            val existingItem = carritoRepository.getCarritoItemByProductId(producto.id)
            if (existingItem != null) {
                // Si el item existe, se actualiza solo la cantidad
                val updatedItem = existingItem.copy(cantidad = existingItem.cantidad + 1)
                carritoRepository.update(updatedItem)
            } else {
                val carritoItem = CarritoItem(
                    productoId = producto.id,
                    nombre = producto.nombre,
                    descripcion = producto.descripcion,
                    precio = producto.precio,
                    cantidad = 1,
                    imageResId = productoImage(producto.nombre)
                )
                carritoRepository.insert(carritoItem)
            }
        }
    }

    fun eliminarDelCarrito(carritoItem: CarritoItem) {
        viewModelScope.launch {
            carritoRepository.delete(carritoItem)
        }
    }

    fun actualizarCantidad(carritoItem: CarritoItem, nuevaCantidad: Int) {
        viewModelScope.launch {
            if (nuevaCantidad > 0) {
                val updatedItem = carritoItem.copy(cantidad = nuevaCantidad)
                carritoRepository.update(updatedItem)
            } else {
                carritoRepository.delete(carritoItem)
            }
        }
    }

    fun vaciarCarrito() {
        limpiarCarrito()
    }

    fun limpiarCarrito() {
        viewModelScope.launch {
            carritoRepository.deleteAll()
        }
    }

    private fun calcularTotal() {
        _totalCarrito.value = _productosCarrito.value.sumOf { it.precio * it.cantidad }
    }

    fun getCantidadTotal(): Int {
        return _productosCarrito.value.sumOf { it.cantidad }
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
}