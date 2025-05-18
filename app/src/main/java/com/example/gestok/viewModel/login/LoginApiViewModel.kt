package com.example.gestok.viewModel.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.gestok.network.service.AuthService
import com.example.gestok.screens.login.data.LoginUser
import com.example.gestok.screens.login.data.UserSession
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginApiViewModel(
    private val api: AuthService, override val _sessaoUsuario : UserSession
) : LoginViewModel(_sessaoUsuario) {

    val autenticado =  mutableStateOf(false)

    override fun login(email: String, senha: String) {
        limparErros()

        var houveErro = false

        if (email.isBlank()) {
            _emailErro = "E-mail é obrigatório"
            houveErro = true
        } else if(!email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))) {
            _emailErro = "E-mail inválido"
            houveErro = true
        }

        if (senha.isBlank()) {
            _senhaErro = "Senha é obrigatória"
            houveErro = true
        }

        if (houveErro) return

        viewModelScope.launch {
            try {
                autenticado.value = false

                val resposta = api.login(LoginUser(email, senha))

                _sessaoUsuario.nome = resposta.nome
                _sessaoUsuario.login = resposta.login
                _sessaoUsuario.cargo = resposta.cargo
                _sessaoUsuario.token = resposta.token
                _sessaoUsuario.idEmpresa = resposta.idEmpresa

                autenticado.value = true
                Log.d("API", "Usuário autenticado com sucesso")

            } catch (e: HttpException) {
                if (e.code() == 403) {
                    _senhaErro = "Credenciais inválidas"
                } else {
                    _senhaErro = "Erro ao processar solicitação"
                    Log.d("API", "Erro ao processar solicitação: ${e.message}")
                }
            } catch (e: Exception) {
                _senhaErro = "Erro ao conectar ao servidor"
                Log.d("API", "Erro ao conectar ao servidor: ${e.message}")
            }
        }
    }
}

