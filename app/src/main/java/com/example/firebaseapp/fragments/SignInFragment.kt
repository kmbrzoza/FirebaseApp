package com.example.firebaseapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.firebaseapp.R
import com.example.firebaseapp.viewmodels.SignInViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignInFragment : Fragment() {
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // If user click back (android button) then return to main fragment
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            view.findNavController().navigate(R.id.action_signInFragment_to_mainFragment)
        }

        viewModel = SignInViewModel((requireNotNull(this.activity).application))

        val signInEmailLayout = view.findViewById<TextInputLayout>(R.id.sign_in_email_layout)
        val signInEmail = view.findViewById<TextInputEditText>(R.id.sign_in_email)

        val signInPasswordLayout = view.findViewById<TextInputLayout>(R.id.sign_in_password_layout)
        val signInPassword = view.findViewById<TextInputEditText>(R.id.sign_in_password)

        view.findViewById<Button>(R.id.button_sign_in).apply {
            setOnClickListener {
                val email = signInEmail.text.toString()
                val password = signInPassword.text.toString()

                var correctValues = true

                if (email.isBlank()) {
                    signInEmailLayout.error = getString(R.string.field_is_required_error)
                    correctValues = false
                }

                if (password.isBlank()) {
                    signInPasswordLayout.error = getString(R.string.field_is_required_error)
                    correctValues = false
                }

                if (correctValues) {
                    viewModel.getAuth().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                view.findNavController()
                                    .navigate(R.id.action_signInFragment_to_movieListFragment)
                            } else {
                                Toast.makeText(
                                    context, "Sign in failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }
}