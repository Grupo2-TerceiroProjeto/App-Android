package com.example.gestok.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(val token: String?): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.i("API", "Token para header: $token")

        val requestBuilder = chain.request().newBuilder()

        if (!token.isNullOrEmpty()) {
            requestBuilder.header("Authorization", "Bearer $token")
        }

        val requisicao = requestBuilder.build()

        return chain.proceed(requisicao)
    }
}