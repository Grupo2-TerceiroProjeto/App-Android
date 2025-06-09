package com.example.gestok.application.config

object ApiConfig {
    const val BACKEND_BASE_URL_LOCAL = "http://localhost:8080/" // Localhost for testing

    const val BACKEND_BASE_URL = "http://13.216.144.118/api/" // Production URL

    private const val CLOUD_NAME = "ddmjnxjm7"

    const val CLOUDINARY_BASE_URL = "https://api.cloudinary.com/v1_1/$CLOUD_NAME/"

    const val LINGVA_BASE_URL = "https://lingva.ml/api/v1/"

    const val SPOONACULAR_BASE_URL = "https://api.spoonacular.com/food/ingredients/"
}