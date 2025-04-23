package com.example.gestok.viewModel.login

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope
import com.example.gestok.network.service.AuthService
import com.example.gestok.screens.login.LoggedInUser
import com.example.gestok.screens.login.LoginUser
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException

class LoginApiViewModel(
    private val api: AuthService, override val _usuarioLogado : LoggedInUser
) : LoginViewModel(_usuarioLogado) {

    val autenticado =  mutableStateOf(false)

    override fun login(email: String, senha: String) {
        limparErros()

        var houveErro = false

        if (email.isBlank() || !email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))) {
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
                autenticado.value = false
                val resposta = withTimeout(5000) { // 5000 milissegundos = 5 segundos
                    api.login(LoginUser(email, senha))
                }

                Log.d("vhtest", usuarioLogado.nome)

                _usuarioLogado.nome = resposta.nome
                _usuarioLogado.login = resposta.login
                _usuarioLogado.cargo = resposta.cargo
                _usuarioLogado.token = resposta.token
                _usuarioLogado.idEmpresa = resposta.idEmpresa


                Log.d("API", "ID da Empresa: ${_usuarioLogado!!.idEmpresa}")

                autenticado.value = true
            } catch (e: HttpException) {
                if (e.code() == 403) {
                    _senhaErro = "Credenciais inválidas"
                } else {
                    _senhaErro = "Erro ao processar solicitação"
                }
            } catch (e: Exception) {
                _senhaErro = "Erro ao conectar ao servidor"
                Log.d("API", "Erro ao conectar ao servidor: ${e.message}")
            }
        }
    }
}

