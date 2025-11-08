package com.example.levelupgamer.data.dao

import androidx.room.*
import com.example.levelupgamer.data.model.CarritoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {
    @Query("SELECT * FROM carrito")
    fun getAllCarritoItems(): Flow<List<CarritoItem>>

    @Query("SELECT * FROM carrito WHERE productoId = :productoId")
    suspend fun getCarritoItemByProductId(productoId: Int): CarritoItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(carritoItem: CarritoItem)

    @Update
    suspend fun update(carritoItem: CarritoItem)

    @Delete
    suspend fun delete(carritoItem: CarritoItem)

    @Query("DELETE FROM carrito")
    suspend fun deleteAll()

    @Query("SELECT SUM(cantidad) FROM carrito")
    fun getTotalItems(): Flow<Int>
}