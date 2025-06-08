package com.example.gestok.domain.model.product

data class Ingrediente(
    val id: Int,
    val nome: String,
    val medida: String,
    val quantidade: Double
)

data class IngredienteComIdSpoonacular(
    val idOriginal: Int,
    val idSpoonacular: Int,
    val medida: String,
    val quantidade: Double
)

data class IngredienteComNutrientes(
    val idOriginal: Int,
    val nutrientes: List<Nutriente>
)

data class IngredienteTraduzido(
    val nomeTraduzido: String,
    val ingredienteOriginal: Ingrediente
)

data class IngredientsModel (
    val id: Int,
    val nome: String,
    val medida: Double,
    val quantidade: Double
)

data class IngredientsFormat (
    val id: Int,
    val nome: String,
    val medida: String,
    val quantidade: Int
)

data class IngredientsProduct (
    val id: Int,
    val nome: String,
    val quantidade: Double
)

data class IngredientsBlock (
    val nome: String,
    val quantidade: Int
)

data class IngredientsRecipe (
    val id: Int,
    val idIngrediente: Int,
    val nome: String,
    val medida: Double,
    val quantidade: Double
)

data class IngredientsBody (
    val nome: String,
    val quantidade: Int,
    val medida: Double,
)


