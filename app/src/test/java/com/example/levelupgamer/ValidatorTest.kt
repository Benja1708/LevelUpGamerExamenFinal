package com.example.levelupgamer

import org.junit.Assert.*
import org.junit.Test

class ValidatorTest {

    @Test
    fun `email valido retorna true`() {
        val result = "usuario@test.com".contains("@")
        assertTrue(result)
    }

    @Test
    fun `email invalido retorna false`() {
        val result = "usuario-sinarroba".contains("@")
        assertFalse(result)
    }
}