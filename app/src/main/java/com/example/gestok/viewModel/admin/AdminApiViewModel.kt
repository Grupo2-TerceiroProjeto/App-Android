package com.example.gestok.viewModel.admin

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.gestok.network.service.AdminService
import com.example.gestok.screens.internalScreens.admin.data.RegisterData
import com.example.gestok.screens.login.data.UserSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class AdminApiViewModel(private val api: AdminService, override val sessaoUsuario: UserSession) :
        AdminViewModel(sessaoUsuario){

        override fun getFuncionarios() {
            limparErros()

            viewModelScope.launch {
                try{
                    delay(1100)

                    val resposta = api.getFuncionarios(sessaoUsuario.idEmpresa)

                    _funcionarios.clear()
                    if(resposta.isNotEmpty()){
                        _funcionarios.addAll(resposta.map{
                            RegisterData(
                                id = it.id,
                                fk_empresa = it.fk_empresa,
                                nome = it.nome,
                                email = it.email,
                                cargo = it.cargo
                            )
                        })
                    }
                    _carregouFuncionarios = true
                    Log.d("API", "Quantidade de funcionarios encontrados: ${funcionarios.size}")
                } catch (e: HttpException){

                    if(e.code() == 500) _carregouFuncionarios = true
                    Log.w("API", "Nenhum funcionario encontrado: ${e.message}  ${_carregouFuncionarios}")
                }  catch (e: Exception){
                    _funcionarioErro = "Erro ao obter dados"
                    Log.e("API", "Erro ao obter dados: ${e.message}")
                }
            }
    }

        }