package com.example.gestok.network

import com.example.gestok.network.service.AdminService
import com.example.gestok.network.service.AuthService
import com.example.gestok.network.service.DashboardService
import com.example.gestok.network.service.OrderService
import com.example.gestok.network.service.ProductService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBackend {

//    private const val BASE_URL = "http://13.216.144.118/api/"

    private const val BASE_URL = "http://localhost:8080/"

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
            .baseUrl(BASE_URL)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun authService(): AuthService {
        return getApi().create(AuthService::class.java)
    }

    fun dashboardService(token: String): DashboardService {
        return getApi(token).create(DashboardService::class.java)
    }

    fun orderService(token: String): OrderService {
        return getApi(token).create(OrderService::class.java)
    }

    fun adminService(token: String): AdminService {
        return getApi(token).create(AdminService::class.java)
    }

    fun productService(token: String): ProductService {
        return getApi(token).create(ProductService::class.java)
    }


}