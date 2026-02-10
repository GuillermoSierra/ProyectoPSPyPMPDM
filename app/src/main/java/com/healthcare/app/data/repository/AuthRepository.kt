package com.healthcare.app.data.repository

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Repository para autenticación con Auth0
 * Maneja login, logout y gestión de tokens
 */
@Singleton
class AuthRepository @Inject constructor(
    private val auth0: Auth0,
    @ApplicationContext private val context: Context
) {

    /**
     * Login con Auth0
     * Retorna las credenciales del usuario
     */
    suspend fun login(): Credentials = suspendCancellableCoroutine { continuation ->
        WebAuthProvider.login(auth0)
            .withScheme("demo")
            .withScope("openid profile email")
            .start(context, object : Callback<Credentials, AuthenticationException> {
                override fun onSuccess(result: Credentials) {
                    continuation.resume(result)
                }

                override fun onFailure(error: AuthenticationException) {
                    continuation.resumeWithException(error)
                }
            })
    }

    /**
     * Logout de Auth0
     */
    suspend fun logout(): Unit = suspendCancellableCoroutine { continuation ->
        WebAuthProvider.logout(auth0)
            .withScheme("demo")
            .start(context, object : Callback<Void?, AuthenticationException> {
                override fun onSuccess(result: Void?) {
                    continuation.resume(Unit)
                }

                override fun onFailure(error: AuthenticationException) {
                    continuation.resumeWithException(error)
                }
            })
    }
}