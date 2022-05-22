package com.example.firebaseapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

open class FormMovie(
    var title: String = "",
    var director: String? = "",
    var year: Int? = null,
    var viewed: Boolean = false,
    var description: String? = ""
)

@Parcelize
data class Movie(var id: String = "") : FormMovie(), Parcelable {
    constructor(id: String = "", formMovie: FormMovie) : this(id) {
        title = formMovie.title
        director = formMovie.director
        year = formMovie.year
        viewed = formMovie.viewed
        description = formMovie.description
    }
}