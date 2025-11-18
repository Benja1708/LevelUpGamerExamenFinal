package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
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
                fecha = "25 Nov 2025",
                resumen = "Inscríbete al torneo presencial de Valorant con premios en hardware gamer.",
                contenido = "Únete a nuestro torneo presencial de Valorant 5v5 en la tienda LevelUp Gamer. " +
                        "Premios para los 3 primeros lugares, sorteos en vivo y descuentos especiales en periféricos."
            ),
            NewsItem(
                id = 2,
                titulo = "Lanzamiento oficial de PlayStation 5 Pro",
                tipo = "Noticia",
                fecha = "10 Dic 2025",
                resumen = "Recibimos stock limitado de PS5 Pro, con bundles exclusivos.",
                contenido = "La nueva PS5 Pro ya llegó a LevelUp Gamer. Tendremos bundles con juegos digitales, " +
                        "controles adicionales y accesorios oficiales. Stock limitado."
            ),
            NewsItem(
                id = 3,
                titulo = "Noche de juegos de mesa – Entrada liberada",
                tipo = "Evento",
                fecha = "02 Dic 2025",
                resumen = "Trae a tus amigos y prueba Catan, Carcassonne y más.",
                contenido = "Organizaremos una noche de juegos de mesa en la tienda. Tendremos monitores explicando " +
                        "reglas, snacks y descuentos especiales en todos los juegos de mesa."
            ),
            NewsItem(
                id = 4,
                titulo = "Ofertas de fin de semana en sillas gamers",
                tipo = "Noticia",
                fecha = "18 Nov 2025",
                resumen = "Hasta un 30% de descuento en modelos seleccionados.",
                contenido = "Solo por este fin de semana, todas las sillas gamers seleccionadas tendrán descuentos " +
                        "desde un 15% hasta un 30%. Consulta términos en tienda."
            )
        )
    }

    fun cambiarFiltro(nuevo: String) {
        _filtro.value = nuevo
    }
}