package com.example.gestok.utils

fun formatDate(input: String): String {
    val cleaned = input.replace(Regex("[^\\d]"), "")
    return when (cleaned.length) {
        in 1..2 -> cleaned
        in 3..4 -> cleaned.substring(0, 2) + "/" + cleaned.substring(2)
        in 5..8 -> cleaned.substring(0, 2) + "/" + cleaned.substring(2, 4) + "/" + cleaned.substring(4)
        else -> cleaned.take(10)
    }
}