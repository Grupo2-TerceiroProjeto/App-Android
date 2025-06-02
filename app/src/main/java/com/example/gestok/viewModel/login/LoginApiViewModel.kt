package com.example.gestok.viewModel.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.gestok.network.service.AuthService
import com.example.gestok.screens.login.data.LoginUser
import com.example.gestok.screens.login.data.SendEmail
import com.example.gestok.screens.login.data.SendEmailResponse
import com.example.gestok.screens.login.data.UserReset
import com.example.gestok.screens.login.data.UserSession
import com.example.gestok.screens.login.data.UserStepReset
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

                _sessaoUsuario.id = resposta.id
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
                    Log.e("API", "Erro ao processar solicitação: ${e.message}")
                }
            } catch (e: Exception) {
                _senhaErro = "Erro ao conectar ao servidor"
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
            }
        }
    }

    override fun enviarEmail(email: String, onSuccess: () -> Unit) {
        limparErros()

        var houveErro = false

        if (email.isBlank()) {
            _emailErro = "E-mail é obrigatório"
            houveErro = true
        } else if(!email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))) {
            _emailErro = "E-mail inválido"
            houveErro = true
        }

        if (houveErro) return

        viewModelScope.launch {
            try {
                val emailRequest = SendEmail(
                    email = email
                )

                val response = api.enviarEmail(emailRequest)
                _emailResponse.value = SendEmailResponse(response.email, response.codigo)
                onSuccess()
                Log.d("API", "E-mail enviado com sucesso. Email: ${_emailResponse.value?.email}, Código: ${_emailResponse.value?.codigo}")
            } catch (e: HttpException) {
                if (e.code() == 500) {
                    _emailNaoEncontrado = "Enviaremos instruções de redefinição de senha por e-mail em breve"
                    Log.e("API", "Email não encontrado ${e.message}")
                }
            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor ${e.message}")
            }
        }
    }

    override fun redefinirSenha(usuario: UserStepReset, onSuccess: () -> Unit) {
        limparErros()

        var houveErro = false

        if (usuario.senha.isBlank()) {
            _senhaErro = "Nova senha é obrigatória"
            houveErro = true
        }

        if (usuario.senha != usuario.confirmarSenha) {
            _confirmarSenhaErro = "As senhas não coincidem"
            houveErro = true
        }

        if (houveErro) return

        viewModelScope.launch {
            try {
                val usuarioRedefinicao = UserReset(
                    email = usuario.email,
                    senha = usuario.senha
                )

                val response = api.redefinirSenha(usuarioRedefinicao)

                if (response.isSuccessful) {
                    onSuccess()
                    Log.d("API", "Senha redefinida com sucesso")
                } else {
                    Log.e("API", "Erro ao redefinir senha: ${response.code()}")
                }

            } catch (e: HttpException) {
                Log.e("API", "Erro ao redefinir senha: ${e.message}")
            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
            }
        }
    }
}

