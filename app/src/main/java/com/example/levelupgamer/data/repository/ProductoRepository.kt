package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.dao.ProductoDao
import com.example.levelupgamer.data.model.Producto
import kotlinx.coroutines.flow.Flow

class ProductoRepository(private val dao: ProductoDao) {

    suspend fun insert(producto: Producto) = dao.insert(producto)

    fun getAllProductos(): Flow<List<Producto>> = dao.getAllProductos()
}
