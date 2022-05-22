package com.example.firebaseapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseapp.R
import com.example.firebaseapp.models.Movie

class MovieListAdapter(private val movies: ArrayList<Movie>?) :
    RecyclerView.Adapter<MovieListAdapter.SubjectsListHolder>() {

    inner class SubjectsListHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textViewMovieTitle: TextView = view.findViewById(R.id.row_movie_tile)
        val rowMovie: ConstraintLayout = view.findViewById(R.id.movie_row)

        fun navigateToMovieInfo(movie: Movie) {
            val bundle = bundleOf(Pair("movie", movie))
            view.findNavController()
                .navigate(R.id.action_movieListFragment_to_formMovieFragment, bundle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectsListHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_row, parent, false)
        return SubjectsListHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectsListHolder, position: Int) {
        if (movies == null) {
            return
        }

        val movie = movies.get(position)

        holder.textViewMovieTitle.text = movie.title

        holder.rowMovie.setOnClickListener {
            holder.navigateToMovieInfo(movie)
        }
    }

    override fun getItemCount(): Int = movies?.size ?: 0
}