package com.healthcare.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.healthcare.app.ui.navigation.HealthCareNavGraph
import com.healthcare.app.ui.theme.HealthCareTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity principal de HealthCare
 * @AndroidEntryPoint habilita inyección de dependencias con Hilt
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthCareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    HealthCareNavGraph(navController = navController)
                }
            }
        }
    }
}