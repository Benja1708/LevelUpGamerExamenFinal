package com.example.levelupgamer.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
// import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.levelupgamer.data.ProductoDatabase
import com.example.levelupgamer.data.model.Producto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.io.IOException
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
@OptIn(ExperimentalCoroutinesApi::class)
class ProductoDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var productoDao: ProductoDao
    private lateinit var db: ProductoDatabase

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ProductoDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        productoDao = db.productoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetAllProductos() = runTest {
        val producto = Producto(nombre = "Test Producto", descripcion = "Desc", precio = 100.0)
        productoDao.insert(producto)

        val allProductos = productoDao.getAllProductos().first()

        assertEquals(1, allProductos.size)
        assertEquals("Test Producto", allProductos.first().nombre)
    }

    @Test
    fun insertOnConflictReplacesExisting() = runTest {
        val original = Producto(id = 1, nombre = "Original", descripcion = "Desc", precio = 100.0)
        productoDao.insert(original)

        val updated = Producto(id = 1, nombre = "Reemplazado", descripcion = "Nueva Desc", precio = 200.0)
        productoDao.insert(updated)

        val retrieved = productoDao.getProductoById(1).first()

        assertEquals("Reemplazado", retrieved.nombre)
        assertEquals(200.0, retrieved.precio, 0.0)
    }

    @Test
    fun updateProducto() = runTest {
        val producto = Producto(nombre = "Viejo Nombre", descripcion = "Vieja Desc", precio = 50.0)
        productoDao.insert(producto)

        val initialList = productoDao.getAllProductosOnce()
        val id = initialList.first().id
        val updatedProducto = initialList.first().copy(nombre = "Nuevo Nombre", precio = 150.0)
        productoDao.update(updatedProducto)

        val retrieved = productoDao.getProductoById(id).first()

        assertEquals("Nuevo Nombre", retrieved.nombre)
        assertEquals(150.0, retrieved.precio, 0.0)
    }

    @Test
    fun deleteProducto() = runTest {
        val producto = Producto(nombre = "A Borrar", descripcion = "Desc", precio = 10.0)
        productoDao.insert(producto)

        val initialList = productoDao.getAllProductosOnce()
        assertEquals(1, initialList.size)

        productoDao.delete(initialList.first())

        val finalList = productoDao.getAllProductosOnce()
        assertTrue(finalList.isEmpty())
    }

    @Test
    fun getAllProductosOnce_returnsCorrectList() = runTest {
        productoDao.insert(Producto(nombre = "A", descripcion = "A", precio = 1.0))
        productoDao.insert(Producto(nombre = "B", descripcion = "B", precio = 2.0))

        val allProductos = productoDao.getAllProductosOnce()

        assertEquals(2, allProductos.size)
    }
}