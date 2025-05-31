package com.example.gestok.di

import com.example.gestok.network.ApiBackend
import com.example.gestok.network.ApiCloudinary
import com.example.gestok.screens.login.data.UserSession
import com.example.gestok.viewModel.admin.AdminApiViewModel
import com.example.gestok.viewModel.dashboard.DashboardApiViewModel
import com.example.gestok.viewModel.login.LoginApiViewModel
import com.example.gestok.viewModel.order.OrderApiViewModel
import com.example.gestok.viewModel.product.ProductApiViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduloGeral = module {

    single<UserSession> {
        UserSession()
    }
}

val moduloApi = module {


    factory {
        ApiBackend.authService()
    }

    factory {
        ApiBackend.dashboardService(
            get<UserSession>().token
        )
    }

    factory {
        ApiBackend.orderService(
            get<UserSession>().token
        )
    }

    factory {
        ApiBackend.adminService(
            get<UserSession>().token
        )
    }

    factory {
        ApiBackend.productService(
            get<UserSession>().token
        )
    }

    factory {
        ApiCloudinary.cloudinaryService()
    }

    viewModel {
        LoginApiViewModel(get(), get())
    }

    viewModel {
        DashboardApiViewModel(get(), get())
    }

    viewModel {
        OrderApiViewModel(get(), get())
    }

    viewModel {
        AdminApiViewModel(get(), get())
    }

    viewModel {
        ProductApiViewModel(get(), get(), get())
    }


}