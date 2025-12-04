package com.example.levelupgamer.repository

import com.example.levelupgamer.repository.FakeUserDao
import com.example.levelupgamer.data.User
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    private lateinit var repository: UserRepository
    private lateinit var fakeDao: FakeUserDao

    @Before
    fun setup() {
        fakeDao = FakeUserDao()
        repository = UserRepository(fakeDao)
    }

    @Test
    fun register_adds_user_correctly() = runBlocking {
        val user = User(
            id = 1,
            nombre = "Benjamin",
            correo = "benja@test.com",
            contrasena = "1234",
            anioNacimiento = 2005,
            puntos = 0,
            referralCode = "ABC123"
        )

        repository.register(user)

        val result = repository.getByEmail("benja@test.com")
        assertThat(result).isNotNull()
        assertThat(result?.nombre).isEqualTo("Benjamin")
    }

    @Test
    fun login_success_returns_user() = runBlocking {
        val user = User(
            id = 1,
            nombre = "Mar",
            correo = "mar@test.com",
            contrasena = "pass",
            anioNacimiento = 2004,
            puntos = 0,
            referralCode = "REF001"
        )

        repository.register(user)

        val result = repository.login("Mar", "pass")

        assertThat(result).isNotNull()
        assertThat(result?.correo).isEqualTo("mar@test.com")
    }

    @Test
    fun login_fail_returns_null() = runBlocking {
        val result = repository.login("NoExiste", "incorrecto")
        assertThat(result).isNull()
    }

    @Test
    fun loginByEmail_success() = runBlocking {
        val user = User(
            id = 2,
            nombre = "Carlos",
            correo = "carlos@test.com",
            contrasena = "abc",
            anioNacimiento = 2000,
            puntos = 0,
            referralCode = "X1"
        )
        repository.register(user)

        val result = repository.loginByEmail("carlos@test.com", "abc")

        assertThat(result).isNotNull()
        assertThat(result?.nombre).isEqualTo("Carlos")
    }

    @Test
    fun updateUser_updates_correctly() = runBlocking {
        val user = User(
            id = 3,
            nombre = "Ana",
            correo = "ana@test.com",
            contrasena = "0000",
            anioNacimiento = 1999,
            puntos = 0,
            referralCode = "REFANA"
        )
        repository.register(user)

        val updated = user.copy(nombre = "Ana María")

        repository.updateUser(updated)

        val result = repository.getUserById(3)
        assertThat(result?.nombre).isEqualTo("Ana María")
    }

    @Test
    fun getByReferralCode_returns_correct_user() = runBlocking {
        val user = User(
            id = 5,
            nombre = "Luis",
            correo = "luis@test.com",
            contrasena = "pass",
            anioNacimiento = 2001,
            puntos = 10,
            referralCode = "REFLUIS"
        )
        repository.register(user)

        val result = repository.getByReferralCode("REFLUIS")

        assertThat(result).isNotNull()
        assertThat(result?.nombre).isEqualTo("Luis")
    }

    @Test
    fun getUserById_returns_correct_user() = runBlocking {
        val user = User(
            id = 7,
            nombre = "Elena",
            correo = "elena@test.com",
            contrasena = "pwd",
            anioNacimiento = 1998,
            puntos = 20,
            referralCode = "REFELENA"
        )
        repository.register(user)

        val result = repository.getUserById(7)

        assertThat(result).isNotNull()
        assertThat(result?.nombre).isEqualTo("Elena")
    }
}
