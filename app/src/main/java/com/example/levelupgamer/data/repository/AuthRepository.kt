package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.model.Credential

class AuthRepository(
    private val validCredential: Credential = Credential.Admin
) {
    fun login(username: String, password: String): Boolean {
        return username == validCredential.username && password == validCredential.password
    }
}
