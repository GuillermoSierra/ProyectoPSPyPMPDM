package com.healthcare.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.result.Credentials
import com.healthcare.app.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Estados de autenticación
 */
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val credentials: Credentials) : AuthState()
    data class Error(val message: String) : AuthState()
    object LoggedOut : AuthState()
}

/**
 * ViewModel para autenticación con Auth0
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail.asStateFlow()

    /**
     * Realizar login con Auth0
     */
    fun login() {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                val credentials = authRepository.login()

                // Guardar email del usuario
                _userEmail.value = credentials.user.email
                _isAuthenticated.value = true
                _authState.value = AuthState.Success(credentials)

            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error al iniciar sesión")
                _isAuthenticated.value = false
            }
        }
    }

    /**
     * Realizar logout
     */
    fun logout() {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                authRepository.logout()

                _userEmail.value = null
                _isAuthenticated.value = false
                _authState.value = AuthState.LoggedOut

            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error al cerrar sesión")
            }
        }
    }

    /**
     * Resetear estado
     */
    fun resetState() {
        _authState.value = AuthState.Idle
    }
}