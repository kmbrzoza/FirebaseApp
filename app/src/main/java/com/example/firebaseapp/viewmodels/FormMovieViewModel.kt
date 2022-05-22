package com.example.firebaseapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.firebaseapp.models.FormMovie
import com.example.firebaseapp.models.Movie
import com.example.firebaseapp.services.FirebaseService

class FormMovieViewModel(application: Application) : AndroidViewModel(application) {
    private val firebaseService: FirebaseService = FirebaseService.getInstance()

    fun addMovie(formMovie: FormMovie) {
        firebaseService.addMovie(formMovie)
    }

    fun updateMovie(movie: Movie) {
        firebaseService.updateMovie(movie)
    }

    fun removeMovie(movie: Movie) {
        firebaseService.removeMovie(movie)
    }
}