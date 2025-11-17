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

    @Query("SELECT * FROM users WHERE nombre = :nombre AND contrasena = :contrasena LIMIT 1")
    suspend fun login(nombre: String, contrasena: String): User?

    @Query("SELECT * FROM users WHERE correo = :correo AND contrasena = :contrasena LIMIT 1")
    suspend fun loginByEmail(correo: String, contrasena: String): User?

    @Query("SELECT * FROM users WHERE correo = :correo LIMIT 1")
    suspend fun getByEmail(correo: String): User?

    @Query("SELECT * FROM users WHERE referralCode = :code LIMIT 1")
    suspend fun getByReferralCode(code: String): User?

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?
}