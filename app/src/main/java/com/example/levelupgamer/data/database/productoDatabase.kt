package com.example.levelupgamer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.levelupgamer.data.dao.ProductoDao
import com.example.levelupgamer.data.model.Producto

@Database(entities = [Producto::class], version = 1, exportSchema = false)
abstract class ProductoDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
}
