package com.example.diptervproto.data.models

import com.google.firebase.database.Exclude
import java.lang.Exception

data class User(var userId: String = "",
                var email: String = "",
                @get:Exclude var isAuthenticated: Boolean = false,
                @get:Exclude var isNewUser: Boolean,
                @get:Exclude var exception: Exception?)