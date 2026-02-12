package com.guillermo.healthcare.data.remote

import com.guillermo.healthcare.data.remote.api.OpenFDAApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClienteRetrofit @Inject constructor() {

    private val interceptorLog = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val clienteOkHttp = OkHttpClient.Builder()
        .addInterceptor(interceptorLog)
        .build()

    val api: OpenFDAApi = Retrofit.Builder()
        .baseUrl("https://api.fda.gov/")
        .client(clienteOkHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OpenFDAApi::class.java)
}