package com.example.levelupgamer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.levelupgamer.data.model.Review
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(review: Review)

    @Query("SELECT * FROM review WHERE productId = :productId ORDER BY createdAt DESC")
    fun getByProduct(productId: Int): Flow<List<Review>>

    @Query("SELECT AVG(rating) FROM review WHERE productId = :productId")
    fun getAverageForProduct(productId: Int): Flow<Double?>
}