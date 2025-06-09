package com.example.gestok.domain.model.auth

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

data class UserReset(
    val email: String,
    val senha: String
)

data class UserStepReset(
    val email: String,
    val senha: String,
    val confirmarSenha: String
)

data class SendEmail(
    val email: String
)

data class SendEmailResponse(
    val email: String,
    val codigo: String
)