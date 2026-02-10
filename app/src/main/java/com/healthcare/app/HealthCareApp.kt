package com.healthcare.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Clase principal de la aplicación
 * @HiltAndroidApp habilita Hilt para inyección de dependencias
 */
@HiltAndroidApp
class HealthCareApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Aquí podrías inicializar librerías globales si es necesario
    }
}