package com.guillermo.healthcare

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.auth0.android.provider.WebAuthProvider
import com.guillermo.healthcare.ui.navigation.GrafoNavegacion
import com.guillermo.healthcare.ui.navigation.Pantalla
import com.guillermo.healthcare.ui.screens.login.ViewModelAuth
import com.guillermo.healthcare.ui.theme.HealthCareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModelAuth: ViewModelAuth by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelAuth.cargarSesionGuardada(this)
        setContent {
            HealthCareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()  // ← AÑADIR

                    @Suppress("StateFlowValueCalledInComposition")
                    val destino = if (viewModelAuth.estado.value.autenticado)
                        Pantalla.Inicio.ruta
                    else
                        Pantalla.Login.ruta

                    GrafoNavegacion(
                        navController = navController,
                        viewModelAuth = viewModelAuth,
                        destinoInicial = destino
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        WebAuthProvider.resume(intent)
    }
}