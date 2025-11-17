package com.example.levelupgamer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.levelupgamer.data.User
import com.example.levelupgamer.data.UserDao
import com.example.levelupgamer.data.dao.ProductoDao
import com.example.levelupgamer.data.model.Producto
import com.example.levelupgamer.data.dao.ReviewDao
import com.example.levelupgamer.data.model.Review

@Database(
    entities = [User::class, Producto::class, Review::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun productoDao(): ProductoDao
    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "levelup_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
