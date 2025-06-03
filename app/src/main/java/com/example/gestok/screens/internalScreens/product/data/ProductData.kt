package com.example.gestok.screens.internalScreens.product.data

data class ProductData(
    val id: Int,
    val nome: String,
    val categoria: Int,
    val preco: Double,
    val quantidade: Int,
    val imagem: String?,
    val emProducao: Boolean
)

data class ProductEditData(
    val nome: String,
    val categoria: Int,
    val preco: Double,
    val quantidade: Int,
    val imagem: String?,
    val emProducao: Boolean
)

data class ProductStepEditData(
    val id: Int,
    val nome: String,
    val categoria: Int,
    val preco: Double,
    val quantidade: Int,
    val imagem: String?,
    val emProducao: Boolean,
    val ingredientes: List<IngredientsRecipe>
)

data class ProductCreateData(
    val nome: String,
    val categoria: Int,
    val preco: Double,
    val quantidade: Int,
    val imagem: String?,
    val emProducao: Boolean,
    val ingredientes: List<IngredientsProduct>
)

data class ProductStepData(
    val nome: String,
    val categoria: Int,
    val subCategoria: Int,
    val preco: Double,
    val quantidade: Int,
    val imagem: String?,
    val emProducao: Boolean,
    val ingredientes: List<Ingrediente>
)