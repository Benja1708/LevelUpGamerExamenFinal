package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.User
import com.example.levelupgamer.data.UserDao
import com.example.levelupgamer.data.model.Credential

class AuthRepository(
    private val userDao: UserDao
) {
    suspend fun login(email: String, password: String): Boolean {
        val user = userDao.loginByEmail(email, password) //

        return user != null
    }
}