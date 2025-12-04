package com.example.levelupgamer.repository

import com.example.levelupgamer.data.User
import com.example.levelupgamer.data.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeUserDao : UserDao {

    private val users = mutableListOf<User>()

    override suspend fun insert(user: User) {
        users.add(user)
    }

    override suspend fun update(user: User) {
        val index = users.indexOfFirst { it.id == user.id }
        if (index != -1) {
            users[index] = user
        }
    }

    override suspend fun login(nombre: String, contrasena: String): User? {
        return users.firstOrNull { it.nombre == nombre && it.contrasena == contrasena }
    }

    override suspend fun loginByEmail(correo: String, contrasena: String): User? {
        return users.firstOrNull { it.correo == correo && it.contrasena == contrasena }
    }

    override suspend fun getByEmail(correo: String): User? {
        return users.firstOrNull { it.correo == correo }
    }

    override suspend fun getByReferralCode(code: String): User? {
        return users.firstOrNull { it.referralCode == code }
    }

    override suspend fun getUserById(userId: Int): User? {
        return users.firstOrNull { it.id == userId }
    }
}
