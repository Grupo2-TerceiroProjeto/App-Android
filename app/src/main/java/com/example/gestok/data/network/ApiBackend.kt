package com.example.gestok.data.network

import com.example.gestok.application.config.ApiConfig
import com.example.gestok.data.repository.AdminRepository
import com.example.gestok.data.repository.AuthRepository
import com.example.gestok.data.repository.DashboardRepository
import com.example.gestok.data.repository.OrderRepository
import com.example.gestok.data.repository.ProductRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBackend {

    fun getApi(token: String? = null): Retrofit {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)


        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)

        if (!token.isNullOrEmpty()) {
            clientBuilder.addInterceptor(TokenInterceptor(token))
        }

        val clienteHttp = clientBuilder.build()

        return Retrofit.Builder()
            .baseUrl(ApiConfig.BACKEND_BASE_URL)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun authRepository(): AuthRepository {
        return getApi().create(AuthRepository::class.java)
    }

    fun dashboardRepository(token: String): DashboardRepository {
        return getApi(token).create(DashboardRepository::class.java)
    }

    fun orderRepository(token: String): OrderRepository {
        return getApi(token).create(OrderRepository::class.java)
    }

    fun adminRepository(token: String): AdminRepository {
        return getApi(token).create(AdminRepository::class.java)
    }

    fun productRepository(token: String): ProductRepository {
        return getApi(token).create(ProductRepository::class.java)
    }


}