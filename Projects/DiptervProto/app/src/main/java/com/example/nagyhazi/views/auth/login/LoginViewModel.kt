package com.example.nagyhazi.views.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.nagyhazi.data.models.User
import com.example.nagyhazi.views.auth.AuthManager

/*
    This ViewModel class is related to LoginActivity.
 */
class LoginViewModel: ViewModel() {

    private val authManager = AuthManager()
    lateinit var authenticatedUserLiveData: LiveData<User>

    // initializes live data
    fun initAuthenticatedUserLiveData() {
        authenticatedUserLiveData = authManager.initLiveData()
    }

    // tries to make user signed in
    fun signInWithEmailAndPassword(email: String, password: String) {
        authenticatedUserLiveData = authManager.signInWithEmailAndPassword(email, password)
    }
}