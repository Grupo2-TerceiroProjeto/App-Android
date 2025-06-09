package com.example.gestok.domain.model.product

data class ProductModel(
    val id: Int,
    val nome: String,
    val categoria: Int,
    val preco: Double,
    val quantidade: Int,
    val imagem: String?,
    val emProducao: Boolean
)

data class ProductEditModel(
    val nome: String,
    val categoria: Int,
    val preco: Double,
    val quantidade: Int,
    val imagem: String?,
    val emProducao: Boolean
)

data class ProductStepEditModel(
    val id: Int,
    val nome: String,
    val categoria: Int,
    val preco: Double,
    val quantidade: Int,
    val imagem: String?,
    val emProducao: Boolean,
    val ingredientes: List<IngredientsRecipe>
)

data class ProductCreateModel(
    val nome: String,
    val categoria: Int,
    val preco: Double,
    val quantidade: Int,
    val imagem: String?,
    val emProducao: Boolean,
    val ingredientes: List<IngredientsProduct>
)

data class ProductStepModel(
    val nome: String,
    val categoria: Int,
    val subCategoria: Int,
    val preco: Double,
    val quantidade: Int,
    val imagem: String?,
    val emProducao: Boolean,
    val ingredientes: List<Ingrediente>
)