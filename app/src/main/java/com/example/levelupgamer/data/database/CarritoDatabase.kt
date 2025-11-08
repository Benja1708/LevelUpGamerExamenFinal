package com.example.levelupgamer.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.levelupgamer.data.dao.CarritoDao
import com.example.levelupgamer.data.model.CarritoItem

@Database(
    entities = [CarritoItem::class],
    version = 1,
    exportSchema = false
)
abstract class CarritoDatabase : RoomDatabase() {
    abstract fun carritoDao(): CarritoDao

    companion object {
        @Volatile
        private var Instance: CarritoDatabase? = null

        fun getDatabase(context: Context): CarritoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    CarritoDatabase::class.java,
                    "carrito_database"
                ).build().also { Instance = it }
            }
        }
    }
}