package com.example.gestok.viewModel.admin

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.gestok.network.service.AdminService
import com.example.gestok.screens.internalScreens.admin.data.RegisterCreateData
import com.example.gestok.screens.internalScreens.admin.data.RegisterData
import com.example.gestok.screens.internalScreens.admin.data.RegisterEditData
import com.example.gestok.screens.login.data.UserSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AdminApiViewModel(private val api: AdminService, override val sessaoUsuario: UserSession) :
    AdminViewModel(sessaoUsuario) {

    override fun getFuncionarios() {
        _funcionariosErro = null

        viewModelScope.launch {
            try {
                delay(1100)

                val resposta = api.getFuncionarios(sessaoUsuario.idEmpresa)

                _funcionarios.clear()
                if (resposta.isNotEmpty()) {
                    _funcionarios.addAll(resposta.map {
                        RegisterData(
                            id = it.id,
                            nome = it.nome,
                            cargo = it.cargo,
                            login = it.login,
                        )
                    })
                }
                _carregouFuncionarios = true
                Log.d("API", "Quantidade de funcionarios encontrados: ${funcionarios.size}")
            } catch (e: HttpException) {
                if (e.code() == 404) _carregouFuncionarios = true
                Log.w(
                    "API",
                    "Nenhum funcionario encontrado: ${e.message}  ${_carregouFuncionarios}"
                )

            } catch (e: Exception) {
                _funcionariosErro = "Erro ao obter dados"
                Log.e("API", "Erro ao obter dados: ${e.message}")
            }
        }
    }

    override fun salvarFuncionario(
        funcionario: RegisterCreateData,
        onBack: () -> Unit,
        onSucess: () -> Unit
    ) {
        limparErrosFormulario()

        var houveErro = false

        if (funcionario.nome.isBlank()) {
            _nomeErro = "Nome do funcionário é obrigatório"
            houveErro = true
        } else if (funcionario.nome.length < 2) {
            _nomeErro = "Nome do funcionário deve ter pelo menos 2 caracteres"
            houveErro = true
        }

        if(funcionario.login.isBlank()){
            _emailErro = "Email do funcionário é obrigatório"
            houveErro = true
        } else if (!funcionario.login.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))) {
            _emailErro = "E-mail inválido"
            houveErro = true
        }

        if (funcionario.cargo == "Selecione uma opção") {
            _cargoErro = "Cargo do funcionário é obrigatório"
            houveErro = true
        }

        if (funcionario.senha.isBlank()) {
            _senhaErro = "Senha do funcionário é obrigatória"
            houveErro = true
        }

        if (houveErro) return

        viewModelScope.launch {
            try {

                val funcionarioFormatado = funcionario .copy(idEmpresa = sessaoUsuario.idEmpresa)

                api.post(funcionarioFormatado)

                Log.d("API", "Funcionário cadastrado com sucesso")

                onSucess()
                getFuncionarios()

                delay(2000)
                onBack()

            } catch (e: HttpException) {
                if (e.code() == 500){}
                Log.w("API", "Erro ao cadastrar funcionário: ${e.message}")
                _cadastroErro = "Já possui um usuário com esses dados"

            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
                _cadastroErro = "Erro ao conectar ao servidor"
            }
        }
    }

    override fun editarFuncionario(
        funcionario: RegisterEditData,
        onBack: () -> Unit,
        onSucess: () -> Unit
    ) {

        limparErrosFormulario()

        var houveErro = false

        if (funcionario.nome.isBlank()) {
            _nomeErro = "Nome do funcionário é obrigatório"
            houveErro = true
        } else if (funcionario.nome.length < 2) {
            _nomeErro = "Nome do funcionário deve ter pelo menos 2 caracteres"
            houveErro = true
        }

        if(funcionario.login.isBlank()){
            _emailErro = "Email do funcionário é obrigatório"
            houveErro = true
        } else if (!funcionario.login.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))) {
            _emailErro = "E-mail inválido"
            houveErro = true
        }

        if (funcionario.cargo == "Selecione uma opção") {
            _cargoErro = "Cargo do funcionário é obrigatório"
            houveErro = true
        }

        if (funcionario.senha.isBlank()) {
            _senhaErro = "Senha do funcionário é obrigatória"
            houveErro = true
        }

        if (houveErro) return

        viewModelScope.launch {
            try {

                val funcionarioFormatado = funcionario .copy(idEmpresa = sessaoUsuario.idEmpresa)

                api.put(funcionarioFormatado)

                Log.d("API", "Funcionário editado com sucesso")

                onSucess()
                getFuncionarios()

                delay(2000)
                onBack()

            } catch (e: HttpException) {
                if (e.code() == 403 || e.code() == 500){}
                Log.e("API", "Erro ao editar funcionário: ${e.message}")
                _edicaoErro = "Erro ao editar funcionário"

            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
                _edicaoErro = "Erro ao conectar ao servidor"
            }
        }
    }

    override fun deletarFuncionario(idFuncionario: Int, onBack: () -> Unit) {
        viewModelScope.launch {
            try {
                api.delete(idFuncionario)

                Log.d("API", "Funcionário excluído com sucesso")

                getFuncionarios()

                delay(2000)
                onBack()

            } catch (e: HttpException) {
                if (e.code() == 500){}
                Log.e("API", "Erro ao excluir funcionário: ${e.message}")

            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
            }
        }
    }

}