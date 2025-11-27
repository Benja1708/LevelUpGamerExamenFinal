package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelupgamer.R // Asegúrate de importar R
import com.example.levelupgamer.data.model.NewsItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewsViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<NewsItem>>(emptyList())
    val items: StateFlow<List<NewsItem>> = _items.asStateFlow()

    private val _filtro = MutableStateFlow("Todos")
    val filtro: StateFlow<String> = _filtro.asStateFlow()

    init {
        _items.value = listOf(
            NewsItem(
                id = 1,
                titulo = "Torneo LevelUp Gamer – Valorant 5v5",
                tipo = "Evento",
                fecha = "18 Ene 2026",
                resumen = "Inscríbete al torneo presencial de Valorant con premios en hardware gamer.",
                contenido = "Únete a nuestro torneo presencial de Valorant 5v5 en la tienda LevelUp Gamer. " +
                        "Premios para los 3 primeros lugares, sorteos en vivo y descuentos especiales en periféricos.",
                imageResId = R.drawable.torneovalorant
            ),
            NewsItem(
                id = 2,
                titulo = "Lanzamiento oficial de PlayStation 5 Pro",
                tipo = "Noticia",
                fecha = "10 Dic 2025",
                resumen = "Recibimos stock limitado de PS5 Pro, con bundles exclusivos.",
                contenido = "La nueva PS5 Pro ya llegó a LevelUp Gamer. Tendremos bundles con juegos digitales, " +
                        "controles adicionales y accesorios oficiales. Stock limitado.",
                imageResId = R.drawable.ps5pro
            ),
            NewsItem(
                id = 3,
                titulo = "Noche de juegos de mesa – Entrada liberada",
                tipo = "Evento",
                fecha = "02 Dic 2025",
                resumen = "Trae a tus amigos y prueba Catan, Carcassonne y más.",
                contenido = "Organizaremos una noche de juegos de mesa en la tienda. Tendremos monitores explicando " +
                        "reglas, snacks y descuentos especiales en todos los juegos de mesa.",
                imageResId = R.drawable.nochejuegosdemesa
            ),
            NewsItem(
                id = 4,
                titulo = "Ofertas de fin de semana en sillas gamers",
                tipo = "Noticia",
                fecha = "18 Nov 2025",
                resumen = "Hasta un 30% de descuento en modelos seleccionados.",
                contenido = "Solo por este fin de semana, todas las sillas gamers seleccionadas tendrán descuentos " +
                        "desde un 15% hasta un 30%. Consulta términos en tienda.",
                imageResId = R.drawable.silla_gamer
            ),
            NewsItem(
                id = 5,
                titulo = "Lanzamiento: Grand Theft Auto VI",
                tipo = "Noticia",
                fecha = "15 May 2025",
                resumen = "Se confirma la llegada de GTA VI en 2025, prometiendo elevar los estándares de la industria.",
                contenido = "El año 2025 se perfila como un periodo crucial para los videojuegos con lanzamientos como 'Grand Theft Auto VI', " +
                        "que no solo elevará el estándar de calidad sino que revitalizará el mercado.",
                imageResId = R.drawable.gtavi
            ),
            NewsItem(
                id = 6,
                titulo = "Anuncio Oficial de Nintendo Switch 2",
                tipo = "Noticia",
                fecha = "20 Nov 2025",
                resumen = "Tras meses de rumores, la sucesora de la consola híbrida se ha lanzado y está batiendo récords de ventas.",
                contenido = "El año 2025 ha sido el año de la Nintendo Switch 2. La consola ha sido un total éxito de ventas en Japón y numerosos mercados importantes.",
                imageResId = R.drawable.nintendo
            ),
            NewsItem(
                id = 7,
                titulo = "Evento: Logitech G - Guía de Regalos",
                tipo = "Evento",
                fecha = "26 Nov 2025",
                resumen = "Logitech presenta su guía de regalos de 2025 con nuevos periféricos gaming.",
                contenido = "La línea PRO ofrece herramientas de alto rendimiento desarrolladas junto a jugadores de esports. Incluye el PRO X SUPERLIGHT 2 y el PRO X TKL, ideales para el juego competitivo.",
                imageResId = R.drawable.logitechplay
            ),
            NewsItem(
                id = 8,
                titulo = "Estrategias para Jugar en la Nube",
                tipo = "Noticia",
                fecha = "04 Nov 2025",
                resumen = "Microsoft, Nvidia, Sony y Amazon siguen apostando por el mercado de juego en la nube en 2025.",
                contenido = "El juego en la nube sigue siendo una apuesta clave. Microsoft (xCloud), Nvidia (GeForce Now), Sony y Amazon están revalorizando sus servicios con enfoques en constancia y alcance multiplataforma.",
                imageResId = R.drawable.cloudgaming
            )
        )
    }

    fun cambiarFiltro(nuevo: String) {
        _filtro.value = nuevo
    }
}