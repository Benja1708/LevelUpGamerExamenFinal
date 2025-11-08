package com.example.levelupgamer.repository

import com.example.levelupgamer.data.User
import com.example.levelupgamer.data.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun register(user: User) {
        userDao.insert(user)
    }

    suspend fun login(nombre: String, contrasena: String): User? {
        return userDao.login(nombre, contrasena)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }
}
