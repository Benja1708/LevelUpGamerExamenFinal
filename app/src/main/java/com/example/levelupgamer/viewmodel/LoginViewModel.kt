package com.example.levelupgamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.database.AppDatabase
import com.example.levelupgamer.data.repository.AuthRepository
import com.example.levelupgamer.ui.screens.login.LoginUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()
    private val authRepository: AuthRepository = AuthRepository(userDao)

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername, error = null)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword, error = null)
    }

    fun submit(onSuccess: () -> Unit) {
        val state = _uiState.value

        if (state.username.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(error = "Correo y/o contraseña no pueden estar vacíos")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, error = null)
            delay(500)

            val loginSuccess = authRepository.login(state.username, state.password)

            if (loginSuccess) {
                _uiState.value = state.copy(isLoading = false, error = null)
                _userEmail.value = state.username
                onSuccess()
            } else {
                _uiState.value = state.copy(isLoading = false, error = "Credenciales incorrectas")
            }
        }
    }
}