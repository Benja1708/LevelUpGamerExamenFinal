package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.dao.ProductoDao
import com.example.levelupgamer.data.model.Producto
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FakeProductoDao : ProductoDao {

    private val productos = mutableListOf<Producto>()
    private val productosFlow = MutableStateFlow<List<Producto>>(emptyList())

    override fun getAllProductos(): Flow<List<Producto>> = productosFlow

    override suspend fun insert(producto: Producto) {
        productos.add(producto)
        productosFlow.value = productos.toList()
    }

    override suspend fun update(producto: Producto) {
        val index = productos.indexOfFirst { it.id == producto.id }
        if (index != -1) {
            productos[index] = producto
            productosFlow.value = productos.toList()
        }
    }

    override suspend fun delete(producto: Producto) {
        productos.removeIf { it.id == producto.id }
        productosFlow.value = productos.toList()
    }

    override suspend fun getAllProductosOnce(): List<Producto> {
        return productos.toList()
    }

    override fun getProductoById(productoId: Int): Flow<Producto> {
        val encontrado = productos.firstOrNull { it.id == productoId }
            ?: throw IllegalArgumentException("Producto no encontrado en Fake DAO")

        return MutableStateFlow(encontrado)
    }
}

class ProductoRepositoryTest {

    private lateinit var repository: ProductoRepository
    private lateinit var fakeDao: FakeProductoDao

    @Before
    fun setup() {
        fakeDao = FakeProductoDao()
        repository = ProductoRepository(fakeDao)
    }

    @Test
    fun insert_producto_adds_to_list() = runBlocking {
        val producto = Producto(
            id = 1,
            codigo = "A001",
            nombre = "Mouse Gamer",
            descripcion = "RGB",
            precio = 15000.0,
            categoria = "Accesorios",
            imagenRes = 100
        )

        repository.insert(producto)

        val result = repository.getAllProductos().first()

        assertThat(result).hasSize(1)
        assertThat(result[0].nombre).isEqualTo("Mouse Gamer")
    }

    @Test
    fun getAllProductos_returns_live_updates() = runBlocking<Unit> {
        val p1 = Producto(
            id = 1,
            codigo = "C1",
            nombre = "Teclado",
            descripcion = "Mecánico",
            precio = 25000.0,
            categoria = "Accesorios",
            imagenRes = 111
        )

        val p2 = Producto(
            id = 2,
            codigo = "C2",
            nombre = "Monitor",
            descripcion = "144Hz",
            precio = 120000.0,
            categoria = "Pantallas",
            imagenRes = 222
        )

        repository.insert(p1)
        repository.insert(p2)

        val result = repository.getAllProductos().first()

        assertThat(result.map { it.nombre })
            .containsExactly("Teclado", "Monitor")
    }
}
