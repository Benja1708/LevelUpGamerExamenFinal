package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.dao.CarritoDao
import com.example.levelupgamer.data.model.CarritoItem
import kotlinx.coroutines.flow.Flow

class CarritoRepository(private val carritoDao: CarritoDao) {

    fun getAllCarritoItems(): Flow<List<CarritoItem>> = carritoDao.getAllCarritoItems()

    suspend fun getCarritoItemByProductId(productoId: Int): CarritoItem? =
        carritoDao.getCarritoItemByProductId(productoId)

    suspend fun insert(carritoItem: CarritoItem) = carritoDao.insert(carritoItem)

    suspend fun update(carritoItem: CarritoItem) = carritoDao.update(carritoItem)

    suspend fun delete(carritoItem: CarritoItem) = carritoDao.delete(carritoItem)

    suspend fun deleteAll() = carritoDao.deleteAll()

    fun getTotalItems(): Flow<Int> = carritoDao.getTotalItems()
}