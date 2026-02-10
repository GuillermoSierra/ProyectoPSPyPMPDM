package com.healthcare.app.di

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.healthcare.app.R
import com.healthcare.app.data.remote.api.MedicineApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Módulo Hilt para Auth0 y Retrofit
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provee Auth0 Account
     */
    @Provides
    @Singleton
    fun provideAuth0(@ApplicationContext context: Context): Auth0 {
        return Auth0(
            context.getString(R.string.com_auth0_domain),
            context.getString(R.string.com_auth0_domain)
        )
    }

    /**
     * Provee Authentication API Client
     */
    @Provides
    @Singleton
    fun provideAuthenticationClient(auth0: Auth0): AuthenticationAPIClient {
        return AuthenticationAPIClient(auth0)
    }

    /**
     * Provee OkHttpClient con logging
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Provee Retrofit para OpenFDA API (medicamentos)
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.fda.gov/") // OpenFDA API
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provee el servicio de API de medicamentos
     */
    @Provides
    @Singleton
    fun provideMedicineApiService(retrofit: Retrofit): MedicineApiService {
        return retrofit.create(MedicineApiService::class.java)
    }
}