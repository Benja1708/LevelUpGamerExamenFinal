package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.dao.ReviewDao
import com.example.levelupgamer.data.model.Review
import kotlinx.coroutines.flow.Flow

class ReviewRepository(private val dao: ReviewDao) {

    fun getReviews(productId: Int): Flow<List<Review>> = dao.getByProduct(productId)

    fun getAverage(productId: Int): Flow<Double?> = dao.getAverageForProduct(productId)

    suspend fun addReview(review: Review) = dao.insert(review)
}