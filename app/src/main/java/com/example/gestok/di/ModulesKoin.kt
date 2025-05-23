package com.example.gestok.di

import com.example.gestok.network.ApiClient
import com.example.gestok.screens.login.UserSession
import com.example.gestok.viewModel.dashboard.DashboardApiViewModel
import com.example.gestok.viewModel.login.LoginApiViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduloGeral = module {

    single<UserSession> {
        UserSession()
    }
}

val moduloApi = module {


    factory {
        ApiClient.authService()
    }

    factory {
        ApiClient.dashboardService(
            get<UserSession>().token ?: ""
        )
    }

    viewModel {
        LoginApiViewModel(get(), get())
    }

    viewModel {
        DashboardApiViewModel(get(), get())
    }


}