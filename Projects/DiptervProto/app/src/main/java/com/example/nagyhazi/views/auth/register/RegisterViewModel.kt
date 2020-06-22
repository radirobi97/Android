package com.example.nagyhazi.views.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.nagyhazi.data.sources.FirebaseDB
import com.example.nagyhazi.data.models.User
import com.example.nagyhazi.views.auth.AuthManager

/*
    This ViewModel class is related to RegisterActivity.
 */
class RegisterViewModel: ViewModel() {

    private val authManager = AuthManager()
    private val firebaseDB = FirebaseDB()
    lateinit var registeredUserLiveData: LiveData<User>

    // initializes live data
    fun initRegisteredUserLiveData() {
        registeredUserLiveData = authManager.initRegisteredLiveData()
    }

    // creates user in firebase
    fun createUserWithEmailAndPassword(email: String, password: String) {
        registeredUserLiveData = authManager.createUserWithEmailAndPassword(email, password)
    }

    // saves user credentials also in firebase realtime database
    fun registerUserInFirebaseDatabase(user: User) {
        firebaseDB.saveUserToDatabase(user)
    }
}