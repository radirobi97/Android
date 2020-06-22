package com.example.nagyhazi.views.auth.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nagyhazi.R
import com.example.nagyhazi.data.models.User
import com.example.nagyhazi.utils.HelperClass
import com.example.nagyhazi.utils.validateNonEmpty
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.android.synthetic.main.activity_register.*

/*
    This class is responsible for registering users.
 */
class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private var authFirebaseReference = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initRegisterViewModel()
        initOnClickListeners()
    }

    /*
        * initializes loginViewModel
        * defines observer to listen authentication changes
            * hides the progressBar
            * displays error/success messages
            * in case of success goes back to login activity
    */
    private fun initRegisterViewModel() {
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        registerViewModel.initRegisteredUserLiveData()
        val registerObserver = Observer<User> { user ->
            if(user.exception != null) {
                displayErrorMessages(user.exception)
                progressBarReg.visibility = View.INVISIBLE
            }
            else {
                // by default email is empty in the live data object so we have to check it
                if(user.email.isNotEmpty()) {
                    registerViewModel.registerUserInFirebaseDatabase(user)
                    Toast.makeText(this, "Registration successful. Please sign in!", Toast.LENGTH_LONG).show()
                    progressBarReg.visibility = View.INVISIBLE
                    finish()
                }
            }
        }
        registerViewModel.registeredUserLiveData.observe(this, registerObserver)
    }

    // handles errors during login
    private fun displayErrorMessages(registerException: Exception?) {
        if(registerException is FirebaseAuthException) {
            val errorCode = (registerException as FirebaseAuthException).errorCode
            HelperClass.handleAuthenticationError(this, errorCode)
        }
    }

    // initializes listeners
    private fun initOnClickListeners() {
        btnRegister.setOnClickListener {
            createUserWithEmailAndPassword()
        }
        tvLogin.setOnClickListener {
            goBackToLoginActivity()
        }
    }

    // creates users in Firebase
    private fun createUserWithEmailAndPassword() {
        if(!validateInputs()) {
            return
        }
        val email = etRegisterEmail.text.toString()
        val password = etRegisterPassword.text.toString()
        progressBarReg.visibility = View.VISIBLE
        // prevents multiple click events on button
        btnRegister.isClickable = false
        registerViewModel.createUserWithEmailAndPassword(email, password)
    }

    // checks whether EditTexts are empty or not
    private fun validateInputs(): Boolean = etRegisterEmail.validateNonEmpty() && etRegisterPassword.validateNonEmpty()

    // finishes RegisterActivity and goes back to LoginActivity
    private fun goBackToLoginActivity() {
        finish()
    }

    // makes user signed out after registration, so user has to login with his new credentials
    override fun onDestroy() {
        super.onDestroy()
        authFirebaseReference.signOut()
    }
}
