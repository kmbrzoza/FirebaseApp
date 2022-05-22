package com.example.firebaseapp.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebaseapp.BuildConfig
import com.example.firebaseapp.models.FormMovie
import com.example.firebaseapp.models.Movie
import com.example.firebaseapp.models.ViewFilter
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class FirebaseService private constructor() {
    companion object {
        private var instance: FirebaseService? = null
        fun getInstance(): FirebaseService {
            return instance ?: FirebaseService()
        }
    }

    private var databaseReference: DatabaseReference
    private var firebaseMovies: ArrayList<Movie> = ArrayList()
    private val firebaseAuthService = FirebaseAuthService.getInstance()

    private var _movies: MutableLiveData<ArrayList<Movie>?> = MutableLiveData(null)
    val movies: LiveData<ArrayList<Movie>?>
        get() {
            return _movies
        }

    init {
        val fireBase =
            FirebaseDatabase.getInstance(BuildConfig.FirebaseProjectUrl)
        databaseReference = fireBase.getReference("MoviesData")
            .child(firebaseAuthService.getUserUid())
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                firebaseMovies = ArrayList()

                for (row in dataSnapshot.children) {
                    val newRow = row.getValue(FormMovie::class.java)
                    val key = row.key

                    if (newRow != null && key != null) {
                        firebaseMovies.add(Movie(key, newRow))
                    }
                }

                _movies.value = firebaseMovies
            }
        })
    }

    fun addMovie(formMovie: FormMovie) {
        databaseReference.child("${Date().time}").setValue(formMovie)
    }

    fun updateMovie(movie: Movie) {
        databaseReference.child(movie.id).setValue(movie as FormMovie)
    }

    fun removeMovie(movie: Movie) {
        databaseReference.child(movie.id).removeValue()
    }

    fun filterMovies(searchValue: String, viewFilter: ViewFilter) {
        val result = firebaseMovies.filter {
            when (viewFilter) {
                ViewFilter.VIEWED -> {
                    it.viewed && it.title.lowercase().contains(searchValue.lowercase())
                }
                ViewFilter.NOTVIEWED -> {
                    !it.viewed && it.title.lowercase().contains(searchValue.lowercase())
                }
                else -> {
                    it.title.lowercase().contains(searchValue.lowercase())
                }
            }
        }
        _movies.value = ArrayList(result)
    }
}