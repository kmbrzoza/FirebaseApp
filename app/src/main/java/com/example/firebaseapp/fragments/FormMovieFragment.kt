package com.example.firebaseapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.firebaseapp.R
import com.example.firebaseapp.extensions.removeErrorOnTextChange
import com.example.firebaseapp.models.FormMovie
import com.example.firebaseapp.models.Movie
import com.example.firebaseapp.viewmodels.FormMovieViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormMovieFragment : Fragment() {
    private lateinit var viewModel: FormMovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_form_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // If user click back (android button) then return to movie list fragment
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateToList()
        }

        viewModel = FormMovieViewModel((requireNotNull(this.activity).application))

        val movieTitle = view.findViewById<TextInputEditText>(R.id.movie_title)
        val movieDirector = view.findViewById<TextInputEditText>(R.id.movie_director)
        val movieYear = view.findViewById<TextInputEditText>(R.id.movie_year)
        val movieViewed = view.findViewById<CheckBox>(R.id.movie_viewed)
        val movieDescription = view.findViewById<TextInputEditText>(R.id.movie_description)

        val movieTitleLayout = view.findViewById<TextInputLayout>(R.id.movie_title_layout)

        val movie = arguments?.get("movie") as Movie?

        if (movie != null) {
            movieTitle.setText(movie.title)
            movieDirector.setText(movie.director)
            movieYear.setText(movie.year?.toString())
            movieViewed.isChecked = movie.viewed
            movieDescription.setText(movie.description)

            val removeButton = view.findViewById<Button>(R.id.button_remove_movie)
            removeButton.visibility = View.VISIBLE
            removeButton.apply {
                setOnClickListener {
                    viewModel.removeMovie(movie)
                    navigateToList()
                    Toast.makeText(context, "Removed successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }

        movieTitle.removeErrorOnTextChange(movieTitleLayout)

        view.findViewById<Button>(R.id.button_add_movie).apply {
            setOnClickListener {
                val title = movieTitle.text.toString()
                val director = movieDirector.text.toString()
                val year = movieYear.text.toString().toIntOrNull()
                val viewed = movieViewed.isChecked
                val description = movieDescription.text.toString()

                if (title.isBlank()) {
                    movieTitleLayout.error = getString(R.string.field_is_required_error)
                } else {
                    val formMovie = FormMovie(title, director, year, viewed, description)

                    if (movie != null) {
                        viewModel.updateMovie(Movie(movie.id, formMovie))
                    } else {
                        viewModel.addMovie(formMovie)
                    }

                    Toast.makeText(context, "Saved successfully", Toast.LENGTH_SHORT).show()
                    navigateToList()
                }
            }
        }
    }

    private fun navigateToList() {
        view?.findNavController()
            ?.navigate(R.id.action_formMovieFragment_to_movieListFragment)
    }
}