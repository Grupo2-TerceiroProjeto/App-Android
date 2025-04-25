package com.example.gestok.viewModel.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gestok.screens.login.UserSession

abstract class LoginViewModel(open val _sessaoUsuario : UserSession) : ViewModel() {

    protected var _emailErro by mutableStateOf<String?>(null)
    protected var _senhaErro by mutableStateOf<String?>(null)

    val emailErro: String?
        get() = _emailErro

    val senhaErro: String?
        get() = _senhaErro

    fun limparErros() {
        _emailErro = null
        _senhaErro = null
    }

    open fun login(email: String, senha: String) {}
}
