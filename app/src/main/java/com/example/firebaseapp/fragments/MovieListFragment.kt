package com.example.firebaseapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseapp.R
import com.example.firebaseapp.adapters.MovieListAdapter
import com.example.firebaseapp.extensions.showDropdownOnClick
import com.example.firebaseapp.models.ViewFilter
import com.example.firebaseapp.viewmodels.MovieListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MovieListFragment : Fragment() {
    private lateinit var viewModel: MovieListViewModel
    private lateinit var filter: AutoCompleteTextView
    private lateinit var filterLayout: TextInputLayout
    private lateinit var search: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // If user click back (android button) then close app
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        viewModel = MovieListViewModel((requireNotNull(this.activity).application))

        view.findViewById<ImageButton>(R.id.logout).apply {
            setOnClickListener {
                viewModel.logoutUser()
                view.findNavController().navigate(R.id.action_movieListFragment_to_mainFragment)
            }
        }

        view.findViewById<TextView>(R.id.movie_list_user_email).apply {
            this.text = viewModel.getUserEmail()
        }

        filter = view.findViewById(R.id.viewed_filter)
        filterLayout = view.findViewById(R.id.viewed_filter_layout)
        search = view.findViewById(R.id.search)

        val recyclerView = view.findViewById<RecyclerView>(R.id.movies_list)
        recyclerView.layoutManager = GridLayoutManager(context, 1)

        viewModel.movies.observe(viewLifecycleOwner) {
            recyclerView.adapter = MovieListAdapter(it)
        }

        view.findViewById<FloatingActionButton>(R.id.fab_add_movie).apply {
            setOnClickListener {
                view.findNavController()
                    .navigate(R.id.action_movieListFragment_to_formMovieFragment)
            }
        }

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.viewed_filters,
            R.layout.list_item
        ).also { adapter ->
            (filterLayout.editText as AutoCompleteTextView).setAdapter(adapter)
        }

        filter.apply {
            setOnClickListener {
                filter.showDropdownOnClick(adapter)
            }

            doOnTextChanged { text, _, _, _ ->
                viewModel.filterMoviesByTypeFilter(getViewFilter(text.toString()))
            }
        }

        search.apply {
            doOnTextChanged { text, _, _, _ ->
                viewModel.filterMoviesBySearchText(text.toString())
            }
        }

        if (filter.text.isNullOrBlank()) {
            filter.setText(ViewFilter.ALL.value)
        }
    }

    private fun getViewFilter(text: String): ViewFilter {
        return ViewFilter.valueOf(
            text.replace(" ", "").uppercase()
        )
    }
}


