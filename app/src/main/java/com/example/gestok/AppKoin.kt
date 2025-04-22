package com.example.gestok

import android.app.Application
import com.example.gestok.di.moduloApi
import com.example.gestok.di.moduloGeral
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppKoin : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()

            androidContext(this@AppKoin)

            modules(moduloGeral, moduloApi)
        }
    }
}