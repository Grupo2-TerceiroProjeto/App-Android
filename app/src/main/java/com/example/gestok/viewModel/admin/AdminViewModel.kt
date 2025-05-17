package com.example.gestok.viewModel.admin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gestok.screens.internalScreens.admin.data.RegisterData
import com.example.gestok.screens.login.data.UserSession

abstract class AdminViewModel (open val sessaoUsuario : UserSession) : ViewModel(){

    protected var _funcionarios = mutableListOf<RegisterData>()

    protected var _funcionarioErro by mutableStateOf<String?>(null)

    protected var _nomeErro by mutableStateOf<String?>(null)

    protected var _emailErro by mutableStateOf<String?>(null)

    protected var _cargoErro by mutableStateOf<String?>(null)

    protected var _carregouFuncionarios by mutableStateOf(false)

    val funcionarios : List<RegisterData>
        get() = _funcionarios.toList()

    val funcionarioErro : String?
        get() = _funcionarioErro

    val nomeErro : String?
        get() = _nomeErro

    val emailErro : String?
        get() = _emailErro

    val cargoErro : String?
        get() = _cargoErro

    val carregouFuncionarios : Boolean
        get() = _carregouFuncionarios

    fun limparErros() {
        _funcionarioErro = null
    }

    open fun getFuncionarios() {}

    open fun salvarFuncionario(funcionario : RegisterData) {}



}




