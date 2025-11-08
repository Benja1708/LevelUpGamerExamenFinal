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

class ProductoViewModel(application: Application) : AndroidViewModel(application) {

    // 👇 este es el MISMO que ya tenía tu proyecto original
    private val db = Room.databaseBuilder(
        application,
        ProductoDatabase::class.java,
        "levelupgamer.db"
    ).build()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    // para no volver a precargar cada vez que llegue el flow
    private var yaPrecargo = false

    init {
        viewModelScope.launch {
            db.productoDao().getAllProductos().collect { lista ->
                _productos.value = lista

                // si está vacío la primera vez, metemos 3 productos de ejemplo
                if (!yaPrecargo && lista.isEmpty()) {
                    yaPrecargo = true
                    precargarProductos()
                }
            }
        }
    }

    private suspend fun precargarProductos() {
        val dao = db.productoDao()
        dao.insert(
            Producto(
                nombre = "Teclado Gamer RGB",
                descripcion = "Teclado mecánico retroiluminado",
                precio = 45990.0
            )
        )
        dao.insert(
            Producto(
                nombre = "Mouse Gamer 16000 DPI!",
                descripcion = "Mouse óptico ultra preciso",
                precio = 29990.0
            )
        )
        dao.insert(
            Producto(
                nombre = "Headset Surround 7.1",
                descripcion = "Audífonos con micrófono",
                precio = 34990.0
            )
        )
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
