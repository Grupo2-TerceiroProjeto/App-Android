package com.example.gestok.screens.internalScreens.admin.data

data class RegisterData(
    val id : Int,
    val nome: String,
    val cargo: String,
    val login: String
)

data class RegisterCreateData(
    val nome: String,
    val login: String,
    val senha: String,
    val cargo: String,
    val idEmpresa: Int
)

data class RegisterEditData(
    val id : Int,
    val nome: String,
    val login: String,
    val senha: String,
    val cargo: String,
    val idEmpresa: Int
)
