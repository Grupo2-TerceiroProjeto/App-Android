package com.example.gestok.screens.login.data

data class LoginUser(
    val login: String,
    val senha: String
)

data class UserSession(
    var id: Int = 0,
    var nome: String = "",
    var login: String = "",
    var cargo: String = "",
    var token: String = "",
    var idEmpresa: Int = 0
)