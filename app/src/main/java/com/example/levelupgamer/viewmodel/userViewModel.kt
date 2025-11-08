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

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    init {
        val dao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(dao)
    }

    fun register(nombre: String, correo: String, contrasena: String, anioNacimiento: Int, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                repository.register(User(nombre = nombre, correo = correo, contrasena = contrasena, anioNacimiento = anioNacimiento))
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

    fun updateUser(user: User, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                repository.updateUser(user)
                _currentUser.value = user
                onComplete(true, null)
            } catch (e: Exception) {
                onComplete(false, e.message)
            }
        }
    }

    fun logout() {
        _currentUser.value = null
    }

    fun loadCurrentUser(nombre: String, contrasena: String) {
        viewModelScope.launch {
            val user = repository.login(nombre, contrasena)
            _currentUser.value = user
        }
    }
}