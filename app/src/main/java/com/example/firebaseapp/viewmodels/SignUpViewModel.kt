package com.example.firebaseapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.firebaseapp.services.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    private val firebaseAuthService = FirebaseAuthService.getInstance()

    fun getAuth(): FirebaseAuth {
        return firebaseAuthService.auth
    }
}