package com.example.gestok.viewModel.admin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gestok.screens.internalScreens.admin.data.RegisterCreateData
import com.example.gestok.screens.internalScreens.admin.data.RegisterData
import com.example.gestok.screens.internalScreens.admin.data.RegisterEditData
import com.example.gestok.screens.login.data.UserSession

abstract class AdminViewModel (open val sessaoUsuario : UserSession) : ViewModel(){

    protected var _funcionarios = mutableStateListOf<RegisterData>()

    protected var _funcionariosErro by mutableStateOf<String?>(null)

    protected var _carregouFuncionarios by mutableStateOf(false)

    protected var _nomeErro by mutableStateOf<String?>(null)

    protected var _emailErro by mutableStateOf<String?>(null)

    protected var _cargoErro by mutableStateOf<String?>(null)

    protected var _senhaErro by mutableStateOf<String?>(null)

    protected var _cadastroErro by mutableStateOf<String?>(null)

    protected var _edicaoErro by mutableStateOf<String?>(null)

    val funcionarios : List<RegisterData>
        get() = _funcionarios.toList()

    val funcionariosErro : String?
        get() = _funcionariosErro

    val carregouFuncionarios : Boolean
        get() = _carregouFuncionarios

    val nomeErro: String?
        get() = _nomeErro

    val emailErro: String?
        get() = _emailErro

    val cargoErro: String?
        get() = _cargoErro

    val senhaErro: String?
        get() = _senhaErro

    val cadastroErro : String?
        get() = _cadastroErro

    val edicaoErro : String?
        get() = _edicaoErro

    fun limparErrosFormulario() {
        _nomeErro = null
        _emailErro = null
        _cargoErro = null
        _senhaErro = null
        _cadastroErro = null
        _edicaoErro = null
    }

    open fun getFuncionarios() {}

    open fun salvarFuncionario(funcionario: RegisterCreateData, onBack: () -> Unit, onSucess: () -> Unit) {}

    open fun editarFuncionario(funcionario: RegisterEditData, onBack: () -> Unit, onSucess: () -> Unit) {}

    open fun deletarFuncionario(idFuncionario: Int, onBack: () -> Unit) {}

}




