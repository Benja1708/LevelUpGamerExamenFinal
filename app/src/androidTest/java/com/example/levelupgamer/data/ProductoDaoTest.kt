package com.example.levelupgamer.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.levelupgamer.data.database.AppDatabase
import com.example.levelupgamer.data.model.Producto
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductoDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: ProductoDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.productoDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insert_and_getAllProductosOnce() = runBlocking {
        val producto = Producto(
            codigo = "P001",
            nombre = "Mouse Gamer",
            descripcion = "Mouse RGB",
            precio = 15000.0,
            categoria = "Accesorios",
            imagenRes = 123
        )

        dao.insert(producto)

        val result = dao.getAllProductosOnce()

        assertThat(result).hasSize(1)
        assertThat(result[0].nombre).isEqualTo("Mouse Gamer")
    }

    @Test
    fun update_producto_updatesCorrectly() = runBlocking {
        val producto = Producto(
            codigo = "T001",
            nombre = "Teclado",
            descripcion = "Teclado Mecánico",
            precio = 25000.0,
            categoria = "Accesorios",
            imagenRes = 111
        )

        dao.insert(producto)

        val inserted = dao.getAllProductosOnce().first()

        val updated = inserted.copy(
            nombre = "Teclado RGB",
            precio = 30000.0
        )

        dao.update(updated)

        val result = dao.getAllProductosOnce().first()

        assertThat(result.nombre).isEqualTo("Teclado RGB")
        assertThat(result.precio).isEqualTo(30000.0)
    }

    @Test
    fun delete_producto() = runBlocking {
        val producto = Producto(
            codigo = "A001",
            nombre = "Audífonos",
            descripcion = "Inalámbricos",
            precio = 20000.0,
            categoria = "Audio",
            imagenRes = 555
        )

        dao.insert(producto)

        val inserted = dao.getAllProductosOnce().first()

        dao.delete(inserted)

        val result = dao.getAllProductosOnce()

        assertThat(result).isEmpty()
    }

    @Test
    fun getProductoById_returnsCorrectProduct() = runBlocking {
        val producto = Producto(
            codigo = "M001",
            nombre = "Monitor",
            descripcion = "144Hz",
            precio = 100000.0,
            categoria = "Monitores",
            imagenRes = 777
        )

        dao.insert(producto)

        val inserted = dao.getAllProductosOnce().first()
        val result = dao.getProductoById(inserted.id).first()

        assertThat(result.nombre).isEqualTo("Monitor")
        assertThat(result.precio).isEqualTo(100000.0)
    }

    @Test
    fun getAllProductos_flow_emitsCorrectValues() = runBlocking {
        val p1 = Producto(
            codigo = "S001",
            nombre = "Silla Gamer",
            descripcion = "Cómoda",
            precio = 120000.0,
            categoria = "Muebles",
            imagenRes = 200
        )

        val p2 = Producto(
            codigo = "M002",
            nombre = "Micrófono",
            descripcion = "USB",
            precio = 30000.0,
            categoria = "Audio",
            imagenRes = 300
        )

        dao.insert(p1)
        dao.insert(p2)

        dao.getAllProductos().test {
            val list = awaitItem()
            assertThat(list).hasSize(2)
            assertThat(list.map { it.nombre })
                .containsExactly("Silla Gamer", "Micrófono")
            cancelAndIgnoreRemainingEvents()
        }
    }
}
