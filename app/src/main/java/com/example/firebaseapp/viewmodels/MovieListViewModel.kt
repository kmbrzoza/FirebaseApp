package com.example.firebaseapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.firebaseapp.models.Movie
import com.example.firebaseapp.models.ViewFilter
import com.example.firebaseapp.services.FirebaseService

class MovieListViewModel(application: Application) : AndroidViewModel(application) {
    val movies: LiveData<ArrayList<Movie>?>
        get() {
            return firebaseService.movies
        }

    private val firebaseService: FirebaseService = FirebaseService.getInstance()
    private var searchText: String = ""
    private var typeFilter: ViewFilter = ViewFilter.ALL

    fun filterMoviesBySearchText(text: String) {
        if (searchText.lowercase() != text.lowercase()) {
            searchText = text
            firebaseService.filterMovies(searchText, typeFilter)
        }
    }

    fun filterMoviesByTypeFilter(filter: ViewFilter) {
        if (typeFilter != filter) {
            typeFilter = filter
            firebaseService.filterMovies(searchText, typeFilter)
        }
    }
}