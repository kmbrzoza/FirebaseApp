package com.example.firebaseapp.helpers

fun hasPasswordCorrectLength(password: String): Boolean {
    if (password.length >= 6) {
        return true
    }
    return false
}

fun isEmailCorrect(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
