package com.example.gestok.presentation.viewmodel.admin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gestok.domain.model.admin.RegisterCreateModel
import com.example.gestok.domain.model.admin.RegisterModel
import com.example.gestok.domain.model.admin.RegisterEditModel
import com.example.gestok.domain.model.auth.UserSession

abstract class AdminViewModel (open val sessaoUsuario : UserSession) : ViewModel(){

    protected var _funcionarios = mutableStateListOf<RegisterModel>()

    protected var _funcionariosErro by mutableStateOf<String?>(null)

    protected var _carregouFuncionarios by mutableStateOf(false)

    protected var _nomeErro by mutableStateOf<String?>(null)

    protected var _emailErro by mutableStateOf<String?>(null)

    protected var _cargoErro by mutableStateOf<String?>(null)

    protected var _senhaErro by mutableStateOf<String?>(null)

    protected var _cadastroErro by mutableStateOf<String?>(null)

    protected var _edicaoErro by mutableStateOf<String?>(null)

    val funcionarios : List<RegisterModel>
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

    open fun salvarFuncionario(funcionario: RegisterCreateModel, onBack: () -> Unit, onSucess: () -> Unit) {}

    open fun editarFuncionario(funcionario: RegisterEditModel, onBack: () -> Unit, onSucess: () -> Unit) {}

    open fun deletarFuncionario(idFuncionario: Int, onBack: () -> Unit) {}

}




