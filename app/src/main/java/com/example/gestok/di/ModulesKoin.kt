package com.example.gestok.di

import com.example.gestok.network.ApiClient
import com.example.gestok.screens.login.LoggedInUser
import com.example.gestok.viewModel.dashboard.DashboardApiViewModel
import com.example.gestok.viewModel.login.LoginApiViewModel
import com.example.gestok.viewModel.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduloGeral = module {

    single<LoggedInUser> {
        LoggedInUser()
    }
}

val moduloApi = module {


    factory {
        ApiClient.authService()
    }

    factory {
        ApiClient.dashboardService(
            get<LoggedInUser>().token ?: ""
        )
    }

    viewModel {
        LoginApiViewModel(get(), get())
    }

    viewModel {
        DashboardApiViewModel(get())
    }

    viewModel {
        LoginViewModel(get())
    }


}