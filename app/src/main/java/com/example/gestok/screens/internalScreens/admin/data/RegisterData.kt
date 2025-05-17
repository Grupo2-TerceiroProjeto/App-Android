package com.example.gestok.screens.internalScreens.admin.data

data class RegisterData(
    val id : Int,
    val nome: String,
    val cargo: String,
    val fk_empresa : Int,
    val email: String
)
