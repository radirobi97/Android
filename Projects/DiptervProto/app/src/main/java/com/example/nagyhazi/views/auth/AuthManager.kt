package com.example.nagyhazi.views.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nagyhazi.data.models.User
import com.google.firebase.auth.FirebaseAuth

class AuthManager {

    private var authenticatedUser = MutableLiveData<User>()
    private var registeredUser = MutableLiveData<User>()

    companion object {

        private val auth = FirebaseAuth.getInstance()

        fun isUserAuthenticated(): Boolean = auth.currentUser != null
    }

    fun signInWithEmailAndPassword(email: String, password: String): LiveData<User> {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                val firebaseUser = auth.currentUser
                val userId = firebaseUser?.uid as String
                val userEmail = firebaseUser.email
                val user = User(
                    userId,
                    email,
                    isAuthenticated = true,
                    isNewUser = false,
                    exception = null
                )
                authenticatedUser.postValue(user)
            }
        }.addOnFailureListener { exception ->
            val user = User(
                "",
                "",
                isAuthenticated = false,
                isNewUser = false,
                exception = exception
            )
            authenticatedUser.postValue(user)
        }
        return authenticatedUser
    }

    fun createUserWithEmailAndPassword(email: String, password: String): LiveData<User> {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                val user = User(
                    "",
                    email,
                    isAuthenticated = false,
                    isNewUser = true,
                    exception = null
                )
                registeredUser.postValue(user)
            }
            else {
                val user = User(
                    "",
                    email,
                    isAuthenticated = false,
                    isNewUser = false,
                    exception = task.exception
                )
                registeredUser.postValue(user)
            }
        }
        return registeredUser
    }

    fun initLiveData(): LiveData<User> {
        authenticatedUser.postValue(
            User(
                "",
                "",
                isAuthenticated = false,
                isNewUser = false,
                exception = null
            )
        )
        return authenticatedUser
    }

    fun initRegisteredLiveData(): LiveData<User> {
        registeredUser.postValue(
            User(
                "",
                "",
                isAuthenticated = false,
                isNewUser = false,
                exception = null
            )
        )
        return registeredUser
    }
}