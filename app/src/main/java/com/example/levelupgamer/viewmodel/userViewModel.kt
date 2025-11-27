package com.example.levelupgamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.User
import com.example.levelupgamer.data.database.AppDatabase
import com.example.levelupgamer.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    init {
        val dao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(dao)
    }

    fun register(
        nombre: String,
        correo: String,
        contrasena: String,
        anioNacimiento: Int,
        codigoReferido: String? = null,
        onComplete: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val yaExiste = repository.getByEmail(correo)
                if (yaExiste != null) {
                    onComplete(false, "Este correo ya está registrado")
                    return@launch
                }

                val miCodigo = generateReferralCode(nombre)
                var puntosIniciales = 0

                if (!codigoReferido.isNullOrBlank()) {
                    val invitador = repository.getByReferralCode(codigoReferido)
                    if (invitador != null) {
                        // invitador gana 10
                        val actualizado = invitador.copy(puntos = invitador.puntos + 10)
                        repository.updateUser(actualizado)

                        puntosIniciales = 10
                    }
                }

                val user = User(
                    nombre = nombre,
                    correo = correo,
                    contrasena = contrasena,
                    anioNacimiento = anioNacimiento,
                    puntos = puntosIniciales,
                    referralCode = miCodigo
                )
                repository.register(user)
                onComplete(true, null)
            } catch (e: Exception) {
                onComplete(false, e.message)
            }
        }
    }

    fun login(nombre: String, contrasena: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = repository.login(nombre, contrasena)
            _currentUser.value = user
            onComplete(user != null)
        }
    }

    fun loginByEmail(correo: String, contrasena: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = repository.loginByEmail(correo, contrasena)
            _currentUser.value = user
            onComplete(user != null)
        }
    }

    // <<-- FUNCIÓN FALTANTE AÑADIDA PARA GUARDAR LOS CAMBIOS -->>
    fun updateUser(user: User, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                // Llama al repositorio para actualizar en la base de datos
                repository.updateUser(user)

                // Actualiza el StateFlow para reflejar los cambios en la UI (MiCuentaScreen)
                _currentUser.value = user

                onComplete(true, null) // Éxito
            } catch (e: Exception) {
                onComplete(false, e.message) // Error
            }
        }
    }
    // <<-- FIN FUNCIÓN FALTANTE -->>

    fun logout() {
        _currentUser.value = null
    }

    private fun generateReferralCode(nombre: String): String {
        val sufijo = Random.nextInt(1000, 9999)
        return nombre.take(2).uppercase() + "-" + sufijo
    }
}