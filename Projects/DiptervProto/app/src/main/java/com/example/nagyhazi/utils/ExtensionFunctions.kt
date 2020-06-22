package com.example.nagyhazi.utils

import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

// checks if EditText contains text
fun EditText.validateNonEmpty(): Boolean {
    if(text.toString().trim().isEmpty()) {
        error = "Required"
        requestFocus()
        return false
    }
    return true
}

// checks if ImageView has drawable
fun ImageView.validateHasImage(context: Context): Boolean {
    if(drawable == null) {
        Toast.makeText(context, "Please choose an image!", Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}