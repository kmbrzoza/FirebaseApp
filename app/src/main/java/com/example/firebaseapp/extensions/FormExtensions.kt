package com.example.firebaseapp.extensions

import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

// WORK AROUND
// https://github.com/material-components/material-components-android/issues/349
fun AutoCompleteTextView.showDropdownOnClick(adapter: ArrayAdapter<CharSequence>?) {
    if (!TextUtils.isEmpty(this.text.toString())) {
        adapter?.filter?.filter(null)
    }
}

fun TextInputEditText.removeErrorOnTextChange(textInputLayout: TextInputLayout) {
    this.apply {
        doOnTextChanged { _, _, _, _ ->
            if (textInputLayout.error != null) {
                textInputLayout.error = null
            }
        }
    }
}

