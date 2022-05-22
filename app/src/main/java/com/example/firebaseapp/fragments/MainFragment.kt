package com.example.firebaseapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.firebaseapp.R
import com.example.firebaseapp.services.FirebaseAuthService

class MainFragment : Fragment() {
    private val firebaseAuthService = FirebaseAuthService.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // If user click back (android button) then close app
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        if (firebaseAuthService.isLogged()) {
            view.findNavController().navigate(R.id.action_mainFragment_to_movieListFragment)
        }

        view.findViewById<Button>(R.id.button_main_sign_in).apply {
            setOnClickListener {
                view.findNavController().navigate(R.id.action_mainFragment_to_signInFragment)
            }
        }

        view.findViewById<Button>(R.id.button_main_sign_up).apply {
            setOnClickListener {
                view.findNavController().navigate(R.id.action_mainFragment_to_signUpFragment)
            }
        }
    }
}