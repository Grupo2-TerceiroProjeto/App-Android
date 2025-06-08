package com.example.gestok.domain.model.admin

data class RegisterModel(
    val id : Int,
    val nome: String,
    val cargo: String,
    val login: String
)

data class RegisterCreateModel(
    val nome: String,
    val login: String,
    val senha: String,
    val cargo: String,
    val idEmpresa: Int
)

data class RegisterEditModel(
    val id : Int,
    val nome: String,
    val login: String,
    val senha: String,
    val cargo: String,
    val idEmpresa: Int
)