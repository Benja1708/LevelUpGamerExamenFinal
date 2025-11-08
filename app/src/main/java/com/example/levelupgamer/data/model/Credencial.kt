package com.example.levelupgamer.data.model


data class Credential(
    val username: String,
    val password: String
) {
    companion object {
        val Admin = Credential("admin", "1234")
    }
}
