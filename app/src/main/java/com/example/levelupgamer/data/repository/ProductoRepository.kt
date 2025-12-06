package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.dao.ProductoDao
import com.example.levelupgamer.data.model.Producto
import com.example.levelupgamer.network.ProductoApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class ProductoRepository(
    private val localDao: ProductoDao,
    private val apiService: ProductoApiService
) {

    suspend fun insert(producto: Producto) {
        try {
            val newProducto = apiService.createProducto(producto)
            localDao.insert(newProducto)
        } catch (e: IOException) {
            println("Error al insertar producto en el API: ${e.message}")
            throw e
        }
    }

    fun getAllProductos(): Flow<List<Producto>> = flow {

        try {
            val productosFromNetwork = apiService.getProductos()
            emit(productosFromNetwork)
        } catch (e: Exception) {
            println("Error de red, cargando datos de la BD local.")
            val productosFromLocal = localDao.getAllProductos()
            throw e
        }
    }
}