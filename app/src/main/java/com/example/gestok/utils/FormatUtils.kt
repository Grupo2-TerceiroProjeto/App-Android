package com.example.gestok.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(input: String): String {
    val cleaned = input.replace(Regex("[^\\d]"), "")
    return when (cleaned.length) {
        in 1..2 -> cleaned
        in 3..4 -> cleaned.substring(0, 2) + "/" + cleaned.substring(2)
        in 5..8 -> cleaned.substring(0, 2) + "/" + cleaned.substring(2, 4) + "/" + cleaned.substring(4)
        else -> cleaned.take(10)
    }
}

fun formatDateApi(data: String): String {
    val formatInput = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = formatInput.parse(data)
    return outputFormat.format(date!!)
}

fun formatPhoneNumber(input: String): String {
    val cleaned = input.replace(Regex("[^\\d]"), "").take(11)
    return when {
        cleaned.length <= 2 -> "(${cleaned}"
        cleaned.length <= 6 -> "(${cleaned.substring(0, 2)}) ${cleaned.substring(2)}"
        cleaned.length <= 10 -> "(${cleaned.substring(0, 2)}) ${cleaned.substring(2, 7)}-${cleaned.substring(7)}"
        else -> "(${cleaned.substring(0, 2)}) ${cleaned.substring(2, 7)}-${cleaned.substring(7)}"
    }
}