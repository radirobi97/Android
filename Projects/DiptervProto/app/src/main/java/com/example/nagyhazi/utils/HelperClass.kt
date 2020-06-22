package com.example.nagyhazi.utils

import android.content.Context
import android.widget.Toast

abstract class HelperClass {

    companion object {

        //handles Firebase authentication errors and display Toast messages
        fun handleAuthenticationError(activityContext: Context, errorCode: String) {
            when(errorCode) {
                "ERROR_INVALID_EMAIL" -> Toast.makeText(activityContext, "Badly formatted email!", Toast.LENGTH_LONG).show()
                "ERROR_WRONG_PASSWORD" -> Toast.makeText(activityContext, "Incorrect password!", Toast.LENGTH_LONG).show()
                "ERROR_USER_NOT_FOUND" -> Toast.makeText(activityContext, "User do not exist!", Toast.LENGTH_LONG).show()
                "ERROR_EMAIL_ALREADY_IN_USE" -> Toast.makeText(activityContext, "User already exists!", Toast.LENGTH_LONG).show()
                "ERROR_WEAK_PASSWORD" -> Toast.makeText(activityContext, "Too short password!", Toast.LENGTH_LONG).show()
            }
        }
    }
}