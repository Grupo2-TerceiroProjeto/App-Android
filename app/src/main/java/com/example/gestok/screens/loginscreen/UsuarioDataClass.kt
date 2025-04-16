package com.example.gestok.screens.loginscreen

data class LoginUser(
    val login: String,
    val senha: String
)

data class LoggedInUser(
    val id: Int,
    val nome: String,
    val login: String,
    val cargo: String,
    val token: String,
    val idEmpresa: Int
)