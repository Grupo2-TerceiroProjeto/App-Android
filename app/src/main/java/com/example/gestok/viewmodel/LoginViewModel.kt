package com.example.gestok.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestok.data.LoggedInUser
import com.example.gestok.data.LoginUser
import com.example.gestok.network.AuthService
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private var _emailErro by mutableStateOf<String?>(null)

    private var _senhaErro by mutableStateOf<String?>(null)

    private var _usuarioLogado by mutableStateOf<LoggedInUser?>(null)

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

    fun login(email: String, senha: String) {
        limparErros()

        var houveErro = false

        if (email.isBlank()) {
            _emailErro = "E-mail inválido"
            houveErro = true
        } else if (!email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))) {
            _emailErro = "E-mail inválido"
            houveErro = true
        }

        if (senha.isBlank()) {
            _senhaErro = "Senha inválida"
            houveErro = true
        }

        if (houveErro) return

        viewModelScope.launch {
            try {
                val resposta = AuthService.UsuarioApi.api.login(LoginUser(email, senha))

                if (resposta != null) {
                    _usuarioLogado = LoggedInUser(
                        nome = resposta.nome,
                        email = resposta.email,
                        cargo = resposta.cargo,
                        idEmpresa = resposta.idEmpresa
                    )
                } else {
                    _senhaErro = "Credenciais inválidas"
                }
            } catch (e: Exception) {
                _senhaErro = "Erro ao conectar ao servidor"
            }
        }
    }
}
