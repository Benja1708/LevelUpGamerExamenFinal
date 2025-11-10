package com.example.levelupgamer.repository

import com.example.levelupgamer.data.User
import com.example.levelupgamer.data.dao.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun register(user: User) {
        userDao.insert(user)
    }

    suspend fun login(nombre: String, contrasena: String): User? {
        return userDao.login(nombre, contrasena)
    }

    suspend fun loginByEmail(correo: String, contrasena: String): User? {
        return userDao.loginByEmail(correo, contrasena)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    suspend fun getByEmail(correo: String): User? {
        return userDao.getByEmail(correo)
    }

    suspend fun getByReferralCode(code: String): User? {
        return userDao.getByReferralCode(code)
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }
}
