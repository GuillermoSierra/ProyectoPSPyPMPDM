package com.guillermo.healthcare.ui.screens.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class EstadoAuth(
    val cargando: Boolean = false,
    val autenticado: Boolean = false,
    val error: String? = null,
    val nombreUsuario: String? = null,
    val emailUsuario: String? = null
)

@HiltViewModel
class ViewModelAuth @Inject constructor() : ViewModel() {

    private val _estado = MutableStateFlow(EstadoAuth())
    val estado: StateFlow<EstadoAuth> = _estado.asStateFlow()

    private val auth0 = Auth0(
        "4ehLNq9ezzcqisqrwGPW7dH5qaa1M3D8",
        "dev-bbezl7kkcc25rf77.us.auth0.com"
    )

    fun iniciarSesion(context: Context) {
        _estado.value = EstadoAuth(cargando = true)

        WebAuthProvider.login(auth0)
            .withScheme("com.guillermo.healthcare")
            .withScope("openid profile email")
            .start(context, object : Callback<Credentials, AuthenticationException> {
                override fun onSuccess(result: Credentials) {
                    _estado.value = EstadoAuth(
                        cargando = false,
                        autenticado = true,
                        nombreUsuario = result.user.name,
                        emailUsuario = result.user.email
                    )
                }

                override fun onFailure(error: AuthenticationException) {
                    _estado.value = EstadoAuth(
                        cargando = false,
                        autenticado = false,
                        error = "Error: ${error.message}"
                    )
                }
            })
    }

    fun cerrarSesion(context: Context) {
        WebAuthProvider.logout(auth0)
            .withScheme("com.guillermo.healthcare")
            .start(context, object : Callback<Void?, AuthenticationException> {
                override fun onSuccess(result: Void?) {
                    _estado.value = EstadoAuth()
                }

                override fun onFailure(error: AuthenticationException) {
                    _estado.value = EstadoAuth()
                }
            })
    }
}