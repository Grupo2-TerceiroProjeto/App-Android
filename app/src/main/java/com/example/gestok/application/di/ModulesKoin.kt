package com.example.gestok.application.di

import com.example.gestok.data.network.ApiBackend
import com.example.gestok.data.network.ApiCloudinary
import com.example.gestok.data.network.ApiLingva
import com.example.gestok.data.network.ApiSpoonacular
import com.example.gestok.domain.model.auth.UserSession
import com.example.gestok.presentation.viewmodel.admin.AdminApiViewModel
import com.example.gestok.presentation.viewmodel.dashboard.DashboardApiViewModel
import com.example.gestok.presentation.viewmodel.login.LoginApiViewModel
import com.example.gestok.presentation.viewmodel.order.OrderApiViewModel
import com.example.gestok.presentation.viewmodel.product.ProductApiViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduloGeral = module {

    single<UserSession> {
        UserSession()
    }
}

val moduloApi = module {


    factory {
        ApiBackend.authRepository()
    }

    factory {
        ApiBackend.dashboardRepository(
            get<UserSession>().token
        )
    }

    factory {
        ApiBackend.orderRepository(
            get<UserSession>().token
        )
    }

    factory {
        ApiBackend.adminRepository(
            get<UserSession>().token
        )
    }

    factory {
        ApiBackend.productRepository(
            get<UserSession>().token
        )
    }

    factory {
        ApiCloudinary.cloudinaryRepository()
    }

    factory {
        ApiLingva.lingvaRepository()
    }

    factory {
        ApiSpoonacular.spoonacularRepository()
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
        ProductApiViewModel(get(), get(), get(), get(), get())
    }


}