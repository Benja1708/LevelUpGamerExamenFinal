package com.example.levelupgamer.data


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.levelupgamer.data.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    // login por nombre (el que ya usabas)
    @Query("SELECT * FROM users WHERE nombre = :nombre AND contrasena = :contrasena LIMIT 1")
    suspend fun login(nombre: String, contrasena: String): User?

    // login por correo (extra)
    @Query("SELECT * FROM users WHERE correo = :correo AND contrasena = :contrasena LIMIT 1")
    suspend fun loginByEmail(correo: String, contrasena: String): User?

    // para ver si el correo ya existe
    @Query("SELECT * FROM users WHERE correo = :correo LIMIT 1")
    suspend fun getByEmail(correo: String): User?

    // para buscar al que invitó
    @Query("SELECT * FROM users WHERE referralCode = :code LIMIT 1")
    suspend fun getByReferralCode(code: String): User?

    // por id
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?
}