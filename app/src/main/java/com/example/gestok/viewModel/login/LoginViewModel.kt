package com.example.gestok.viewModel.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gestok.screens.login.LoggedInUser

abstract class LoginViewModel : ViewModel() {

    protected var _emailErro by mutableStateOf<String?>(null)
    protected var _senhaErro by mutableStateOf<String?>(null)
    protected var _usuarioLogado by mutableStateOf<LoggedInUser?>(null)

    val emailErro: String?
        get() = _emailErro

    val senhaErro: String?
        get() = _senhaErro

    val usuarioLogado: LoggedInUser?
        get() = _usuarioLogado

    fun limparErros() {
        _emailErro = null
        _senhaErro = null
    }

    open fun login(email: String, senha: String) {}
}
