package com.example.levelupgamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.database.AppDatabase
import com.example.levelupgamer.data.model.Review
import com.example.levelupgamer.data.repository.ReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReviewViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: ReviewRepository =
        ReviewRepository(AppDatabase.getDatabase(application).reviewDao())

    // producto “actual”
    private val _productId = MutableStateFlow<Int?>(null)
    val productId: StateFlow<Int?> = _productId

    // lista de reseñas observable
    val reviews: StateFlow<List<Review>> =
        _productId.flatMapLatest { id ->
            if (id == null) kotlinx.coroutines.flow.flowOf(emptyList())
            else repo.getReviews(id)
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // promedio observable
    val average: StateFlow<Double?> =
        _productId.flatMapLatest { id ->
            if (id == null) kotlinx.coroutines.flow.flowOf(null)
            else repo.getAverage(id)
        }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun setProduct(id: Int) { _productId.value = id }

    fun addReview(
        rating: Int,
        comment: String,
        userName: String
    ) {
        val pid = _productId.value ?: return
        viewModelScope.launch {
            repo.addReview(
                Review(
                    productId = pid,
                    userName = userName.ifBlank { "Anónimo" },
                    rating = rating.coerceIn(1, 5),
                    comment = comment.trim()
                )
            )
        }
    }
}