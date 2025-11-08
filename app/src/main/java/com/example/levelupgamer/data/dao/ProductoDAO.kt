package com.example.levelupgamer.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.levelupgamer.data.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Query("SELECT * FROM producto")
    fun getAllProductos(): Flow<List<Producto>>

    @Query("SELECT * FROM producto WHERE id = :productoId")
    fun getProductoById(productoId: Int): Flow<Producto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto)

    @Update
    suspend fun update(producto: Producto)

    @Delete
    suspend fun delete(producto: Producto)

    @Query("SELECT * FROM producto")
    suspend fun getAllProductosOnce(): List<Producto>
}
