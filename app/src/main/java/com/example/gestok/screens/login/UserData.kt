package com.example.gestok.screens.login

data class LoginUser(
    val login: String,
    val senha: String
)

data class LoggedInUser(
    val nome: String,
    val login: String,
    val cargo: String,
    val idEmpresa: Int
)