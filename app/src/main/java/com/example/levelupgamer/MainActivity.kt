package com.example.levelupgamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.lifecycleScope
import com.example.levelupgamer.data.database.AppDatabase
import com.example.levelupgamer.data.model.Producto
import com.example.levelupgamer.ui.AppNavigation
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        precargarProductos()

        setContent {
            LevelUpGamerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }

    private fun precargarProductos() {
        val db = AppDatabase.getDatabase(this)
        val productoDao = db.productoDao()

        lifecycleScope.launch {
            val existentes = productoDao.getAllProductosOnce()
            if (existentes.isEmpty()) {
                val productos = listOf(
                    Producto(
                        codigo = "JM001",
                        nombre = "Catan",
                        descripcion = "Un clásico juego de estrategia donde los jugadores compiten por colonizar y\n" +
                                "expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en\n" +
                                "familia o con amigos.",
                        precio = 29990.0,
                        categoria = "Juegos de mesa",
                        imagenRes = R.drawable.catan
                    ),
                    Producto(
                        codigo = "JM002 ",
                        nombre = "Carcassonne",
                        descripcion = "Un juego de colocación de fichas donde los jugadores construyen el paisaje\n" +
                                "alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de\n" +
                                "aprender.\n",
                        precio = 24990.0,
                        categoria = "Juegos de mesa",
                        imagenRes = R.drawable.carcassonne
                    ),
                    Producto(
                        codigo = "AC001",
                        nombre = "Controlador Inalámbrico Xbox Series X",
                        descripcion = "GOfrece una experiencia de juego cómoda con\n" +
                                "botones mapeables y una respuesta táctil mejorada. Compatible con consolas Xbox y PC.",
                        precio = 59990.0,
                        categoria = "Accesorios",
                        imagenRes = R.drawable.control_xbox
                    ),
                    Producto(
                        codigo = "AC002",
                        nombre = "Auriculares Gamer HyperX Cloud II",
                        descripcion = "Proporcionan un sonido envolvente de calidad con un\n" +
                                "micrófono desmontable y almohadillas de espuma viscoelástica para mayor comodidad\n" +
                                "durante largas sesiones de juego.\n",
                        precio = 79990.0,
                        categoria = "Accesorios",
                        imagenRes = R.drawable.audifonos_hyperx
                    ),
                    Producto(
                        codigo = "CO001",
                        nombre = "PlayStation 5",
                        descripcion = "La consola de última generación de Sony, que ofrece gráficos\n" +
                                "impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.",
                        precio = 549990.0,
                        categoria = "Consolas",
                        imagenRes = R.drawable.ps5
                    ),
                    Producto(
                        codigo = "CG001",
                        nombre = "PC Gamer ASUS ROG Strix",
                        descripcion = " Un potente equipo diseñado para los gamers más exigentes,\n" +
                                "equipado con los últimos componentes para ofrecer un rendimiento excepcional en\n" +
                                "cualquier juego.",
                        precio = 1299990.0,
                        categoria = "Computadores gamers",
                        imagenRes = R.drawable.pc_gamer_asus
                    ),
                    Producto(
                        codigo = "SG001",
                        nombre = "Silla Gamer",
                        descripcion = " Diseñada para el máximo confort, esta silla ofrece un soporte\n" +
                                "ergonómico y personalización ajustable para sesiones de juego prolongadas.",
                        precio = 99990.0,
                        categoria = "Sillas gamers",
                        imagenRes = R.drawable.silla_gamer
                    ),
                    Producto(
                        codigo = "MS001",
                        nombre = "Mouse Gamer 16000 DPI",
                        descripcion = "Con sensor de alta precisión y botones\n" +
                                "personalizables, este mouse es ideal para gamers que buscan un control preciso y\n" +
                                "personalización.\n",
                        precio = 29990.0,
                        categoria = "Mouse",
                        imagenRes = R.drawable.mouse_gamer
                    ),
                    Producto(
                        codigo = "MP001",
                        nombre = "Mousepad XL RGB",
                        descripcion = " Ofrece un área de juego amplia con\n" +
                                "iluminación RGB personalizable, asegurando una superficie suave y uniforme para el\n" +
                                "movimiento del mouse.\n",
                        precio = 19990.0,
                        categoria = "Mousepad",
                        imagenRes = R.drawable.mousepad_gamer
                    ),
                    Producto(
                        codigo = "PP001",
                        nombre = "Polera Gamer Personalizada 'Level-Up'",
                        descripcion = "Una camiseta cómoda y estilizada, con la\n" +
                                "posibilidad de personalizarla con tu gamer tag o diseño favorito.",
                        precio = 34990.0,
                        categoria = "Polera gamer",
                        imagenRes = R.drawable.polera_gamer
                    )
                )

                productos.forEach { productoDao.insert(it) }
            }
        }
    }
}