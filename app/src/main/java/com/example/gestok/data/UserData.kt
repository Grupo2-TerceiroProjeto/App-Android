package com.example.gestok.data

data class LoginUser(
    val email: String,
    val senha: String
)

data class LoggedInUser(
    val nome: String,
    val email: String,
    val cargo: String,
    val idEmpresa: Int
)