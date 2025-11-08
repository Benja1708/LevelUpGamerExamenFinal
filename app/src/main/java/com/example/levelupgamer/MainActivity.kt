import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.lifecycleScope
import com.example.levelupgamer.data.database.AppDatabase
import com.example.levelupgamer.data.model.Producto
import com.example.levelupgamer.ui.AppNavigation
import com.example.levelupgamer.ui.theme.LevelUPGamerTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1) precarga la tabla producto si está vacía
        precargarProductos()

        // 2) tu UI de siempre
        setContent {
            LevelUPGamerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }

    private fun precargarProductos() {
        // obtenemos la DB
        val db = AppDatabase.getDatabase(this)
        val productoDao = db.productoDao()

        // como hay que llamar a Room en corrutina:
        lifecycleScope.launch {
            // ¿ya hay productos?
            val existentes = productoDao.getAllProductos().first()
            if (existentes.isEmpty()) {
                // si no hay, los insertamos
                productoDao.insert(
                    Producto(
                        nombre = "Teclado Mecánico RGB",
                        descripcion = "Teclado gamer retroiluminado con switches azules.",
                        precio = 39990.0
                    )
                )
                productoDao.insert(
                    Producto(
                        nombre = "Mouse Gamer 7200 DPI",
                        descripcion = "Mouse preciso e inalámbrico para gaming.",
                        precio = 19990.0
                    )
                )
                productoDao.insert(
                    Producto(
                        nombre = "Silla Gamer Pro",
                        descripcion = "Silla ergonómica con soporte lumbar.",
                        precio = 89990.0
                    )
                )
                productoDao.insert(
                    Producto(
                        nombre = "Headset 7.1 Surround",
                        descripcion = "Audífonos gamer con micrófono.",
                        precio = 29990.0
                    )
                )
            }
        }
    }
}