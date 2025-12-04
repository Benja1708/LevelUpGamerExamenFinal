package com.example.levelupgamer.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.levelupgamer.data.database.AppDatabase
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries() // OK para pruebas
            .build()

        userDao = db.userDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertUser_and_getById() = runBlocking {
        val user = User(
            id = 1,
            nombre = "Benjamin",
            correo = "benja@example.com",
            contrasena = "1234",
            anioNacimiento = 2005,
            puntos = 0,
            referralCode = "ABC123"
        )

        userDao.insert(user)

        val result = userDao.getUserById(1)

        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result?.nombre).isEqualTo("Benjamin")
        Truth.assertThat(result?.correo).isEqualTo("benja@example.com")
    }

    @Test
    fun login_success() = runBlocking {
        val user = User(
            id = 2,
            nombre = "Mar",
            correo = "mar@example.com",
            contrasena = "pass",
            anioNacimiento = 2004,
            puntos = 50,
            referralCode = "XYZ789"
        )

        userDao.insert(user)

        val logged = userDao.login("Mar", "pass")

        Truth.assertThat(logged).isNotNull()
        Truth.assertThat(logged?.correo).isEqualTo("mar@example.com")
    }

    @Test
    fun login_fail_wrongPassword() = runBlocking {
        val user = User(
            id = 3,
            nombre = "Test",
            correo = "t@example.com",
            contrasena = "secret",
            anioNacimiento = 2000,
            puntos = 0,
            referralCode = "YYY111"
        )

        userDao.insert(user)

        val logged = userDao.login("Test", "wrong")

        Truth.assertThat(logged).isNull()
    }

    @Test
    fun getByEmail_returnsUser() = runBlocking {
        val user = User(
            id = 4,
            nombre = "MailUser",
            correo = "mail@example.com",
            contrasena = "mailpass",
            anioNacimiento = 1990,
            puntos = 10,
            referralCode = ""
        )

        userDao.insert(user)

        val result = userDao.getByEmail("mail@example.com")

        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result?.nombre).isEqualTo("MailUser")
    }

    @Test
    fun getByReferralCode_returnsUser() = runBlocking {
        val user = User(
            id = 5,
            nombre = "Referido",
            correo = "ref@example.com",
            contrasena = "1234",
            anioNacimiento = 1995,
            puntos = 20,
            referralCode = "CODE999"
        )

        userDao.insert(user)

        val result = userDao.getByReferralCode("CODE999")

        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result?.correo).isEqualTo("ref@example.com")
    }

    @Test
    fun updateUser_updatesCorrectly() = runBlocking {
        val user = User(
            id = 6,
            nombre = "Pepe",
            correo = "pepe@example.com",
            contrasena = "abc",
            anioNacimiento = 1980,
            puntos = 0,
            referralCode = ""
        )

        userDao.insert(user)

        val updated = user.copy(nombre = "PepeModificado", puntos = 100)
        userDao.update(updated)

        val result = userDao.getUserById(6)

        Truth.assertThat(result?.nombre).isEqualTo("PepeModificado")
        Truth.assertThat(result?.puntos).isEqualTo(100)
    }
}