package com.example.levelupgamer.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.levelupgamer.data.database.AppDatabase
import com.example.levelupgamer.data.model.Review
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReviewDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: ReviewDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.reviewDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insert_and_getByProduct_returnsCorrectReviews() = runBlocking {
        val r1 = Review(
            productId = 10,
            userName = "Benjamin",
            rating = 5,
            comment = "Excelente producto"
        )

        val r2 = Review(
            productId = 10,
            userName = "Mar",
            rating = 4,
            comment = "Muy bueno"
        )

        dao.insert(r1)
        dao.insert(r2)

        dao.getByProduct(10).test {
            val list = awaitItem()
            assertThat(list).hasSize(2)
            assertThat(list.map { it.comment })
                .containsExactly("Muy bueno", "Excelente producto")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun reviews_are_sorted_by_createdAt_desc() = runBlocking {

        val oldReview = Review(
            productId = 20,
            userName = "Antiguo",
            rating = 3,
            comment = "Comentario viejo",
            createdAt = 1L // más antiguo
        )

        val newReview = Review(
            productId = 20,
            userName = "Nuevo",
            rating = 5,
            comment = "Comentario reciente",
            createdAt = 99999999999L
        )

        dao.insert(oldReview)
        dao.insert(newReview)

        val result = dao.getByProduct(20).first()

        assertThat(result[0].comment).isEqualTo("Comentario reciente")
        assertThat(result[1].comment).isEqualTo("Comentario viejo")
    }

    @Test
    fun average_rating_is_correct() = runBlocking {
        val r1 = Review(productId = 30, userName = "A", rating = 5, comment = "a")
        val r2 = Review(productId = 30, userName = "B", rating = 3, comment = "b")
        val r3 = Review(productId = 30, userName = "C", rating = 4, comment = "c")

        dao.insert(r1)
        dao.insert(r2)
        dao.insert(r3)

        val avg = dao.getAverageForProduct(30).first()

        assertThat(avg).isEqualTo(4.0)
    }
    @Test
    fun average_is_null_when_no_reviews() = runBlocking {
        val avg = dao.getAverageForProduct(999).first()
        assertThat(avg).isNull()
    }
    @Test
    fun getByProduct_returns_only_target_reviews() = runBlocking {

        val r1 = Review(productId = 1, userName = "A", rating = 5, comment = "OK")
        val r2 = Review(productId = 2, userName = "B", rating = 2, comment = "MAL")

        dao.insert(r1)
        dao.insert(r2)

        val result = dao.getByProduct(1).first()

        assertThat(result).hasSize(1)
        assertThat(result[0].productId).isEqualTo(1)
    }
}
