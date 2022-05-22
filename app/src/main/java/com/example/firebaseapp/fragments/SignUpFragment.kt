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
import com.example.firebaseapp.extensions.removeErrorOnTextChange
import com.example.firebaseapp.helpers.hasPasswordCorrectLength
import com.example.firebaseapp.helpers.isEmailCorrect
import com.example.firebaseapp.viewmodels.SignUpViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignUpFragment : Fragment() {
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // If user click back (android button) then return to main fragment
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            view.findNavController().navigate(R.id.action_signUpFragment_to_mainFragment)
        }

        viewModel = SignUpViewModel((requireNotNull(this.activity).application))

        val signUpEmailLayout = view.findViewById<TextInputLayout>(R.id.sign_up_email_layout)
        val signUpEmail = view.findViewById<TextInputEditText>(R.id.sign_up_email)

        val signUpPasswordLayout =
            view.findViewById<TextInputLayout>(R.id.sign_up_password_layout)
        val signUpPassword = view.findViewById<TextInputEditText>(R.id.sign_up_password)

        val signUpPasswordConfirmLayout =
            view.findViewById<TextInputLayout>(R.id.sign_up_password_confirm_layout)
        val signUpPasswordConfirm =
            view.findViewById<TextInputEditText>(R.id.sign_up_password_confirm)

        signUpEmail.removeErrorOnTextChange(signUpEmailLayout)
        signUpPassword.removeErrorOnTextChange(signUpPasswordLayout)
        signUpPasswordConfirm.removeErrorOnTextChange(signUpPasswordConfirmLayout)

        view.findViewById<Button>(R.id.button_sign_up).apply {
            setOnClickListener {
                val email = signUpEmail.text.toString()
                val password = signUpPassword.text.toString()
                val passwordConfirm = signUpPasswordConfirm.text.toString()

                var correctValues = true

                if (email.isBlank()) {
                    signUpEmailLayout.error = getString(R.string.field_is_required_error)
                    correctValues = false
                } else if (!isEmailCorrect(email)) {
                    signUpEmailLayout.error = getString(R.string.incorrect_email_format)
                    correctValues = false
                }

                if (password.isBlank()) {
                    signUpPasswordLayout.error = getString(R.string.field_is_required_error)
                    correctValues = false
                } else if (!hasPasswordCorrectLength(password)) {
                    signUpPasswordLayout.error = getString(R.string.password_length_error)
                    correctValues = false
                }

                if (passwordConfirm.isBlank()) {
                    signUpPasswordConfirmLayout.error = getString(R.string.field_is_required_error)
                    correctValues = false
                } else if (password != passwordConfirm) {
                    signUpPasswordConfirmLayout.error =
                        getString(R.string.passwords_are_different_error)
                    correctValues = false
                }

                if (correctValues) {
                    viewModel.getAuth().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                view.findNavController()
                                    .navigate(R.id.action_signUpFragment_to_movieListFragment)
                            } else {
                                Toast.makeText(
                                    context, "Sign up failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }
}