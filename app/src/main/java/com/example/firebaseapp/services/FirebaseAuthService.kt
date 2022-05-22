package com.example.firebaseapp.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthService private constructor() {
    companion object {
        private var instance: FirebaseAuthService? = null
        fun getInstance(): FirebaseAuthService {
            return instance ?: FirebaseAuthService()
        }
    }

    var auth: FirebaseAuth = Firebase.auth
        private set

    fun isLogged(): Boolean {
        return auth.currentUser != null
    }

    fun logout() {
        auth.signOut()
    }

    fun getUserUid(): String {
        if (!isLogged()) {
            throw Exception("Unauthenticated user")
        }
        return auth.currentUser!!.uid
    }

    fun getUserEmail(): String {
        if (!isLogged()) {
            throw Exception("Unauthenticated user")
        }
        return auth.currentUser!!.email!!
    }
}