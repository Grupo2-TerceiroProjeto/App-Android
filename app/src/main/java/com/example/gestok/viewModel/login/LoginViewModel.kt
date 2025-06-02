package com.example.gestok.viewModel.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.gestok.screens.login.data.SendEmailResponse
import com.example.gestok.screens.login.data.UserSession
import com.example.gestok.screens.login.data.UserStepReset

abstract class LoginViewModel(open val _sessaoUsuario : UserSession) : ViewModel() {

    protected var _emailErro by mutableStateOf<String?>(null)
    protected var _senhaErro by mutableStateOf<String?>(null)
    protected var _confirmarSenhaErro by mutableStateOf<String?>(null)

    protected var _emailNaoEncontrado by mutableStateOf<String?>(null)
    var _codigoErro by mutableStateOf<String?>(null)

    protected val _emailResponse = mutableStateOf<SendEmailResponse?>(null)

    val emailErro: String?
        get() = _emailErro

    val senhaErro: String?
        get() = _senhaErro

    val confirmarSenhaErro: String?
        get() = _confirmarSenhaErro

    val emailNaoEncontrado: String?
        get() = _emailNaoEncontrado

    val codigoErro: String?
        get() = _codigoErro

    val emailResponse: State<SendEmailResponse?> = _emailResponse

    fun limparErros() {
        _emailErro = null
        _senhaErro = null
        _confirmarSenhaErro = null
        _emailNaoEncontrado = null
        _codigoErro = null
    }

    open fun login(email: String, senha: String) {}

    open fun enviarEmail(email: String, onSuccess: () -> Unit) {}

    open fun redefinirSenha(usuario: UserStepReset, onSuccess: () -> Unit) {}
}
