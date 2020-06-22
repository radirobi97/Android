package com.example.nagyhazi.views.auth.login

import android.app.Activity
import android.content.Intent
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
import com.example.nagyhazi.views.auth.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.android.synthetic.main.activity_login.*

/*
    This class is responsible for handling login processes.
 */
class LoginActivity : AppCompatActivity() {

    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initLoginViewModel()
        initOnClickListeners()
    }

    /*
        * initializes loginViewModel
        * defines observer to listen authentication changes
            * hides the progressBar
            * displays error/success messages
            - in case of success goes back to previous activity
    */
    private fun initLoginViewModel() {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.initAuthenticatedUserLiveData()
        val loginObserver = Observer<User> { user ->
            // handles exceptions if there were any
            if(user.exception != null) {
                displayErrorMessages(user.exception)
                progressBarLogin.visibility = View.INVISIBLE
                // makes again clickable
                btnLogin.isClickable = true
            }
            else {
                // by default email is empty in the live data object so we have to check it
                if (user.email.isNotEmpty()) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_LONG).show()
                    progressBarLogin.visibility = View.INVISIBLE
                    val resultIntent = Intent()
                    setResult(Activity.RESULT_CANCELED, resultIntent)
                    finish()
                }
            }
        }
        loginViewModel.authenticatedUserLiveData.observe(this, loginObserver)
    }

    // handles errors during login
    private fun displayErrorMessages(loginException: Exception?) {
        if(loginException is FirebaseAuthException) {
            val errorCode = (loginException as FirebaseAuthException).errorCode
            HelperClass.handleAuthenticationError(this, errorCode)
        }
    }

    // initializes listeners
    private fun initOnClickListeners() {
        btnLogin.setOnClickListener {
            signInWithEmailAndPassword()
        }
        tvRegisterHere.setOnClickListener {
            goToRegisterActivity()
        }
    }

    // signs in to Firebase
    private fun signInWithEmailAndPassword() {
        if(!validateInputs()) {
            return
        }
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        progressBarLogin.visibility = View.VISIBLE
        // prevents multiple click events on login button
        btnLogin.isClickable = false
        loginViewModel.signInWithEmailAndPassword(email, password)
    }

    // checks whether EditTexts are empty or not
    private fun validateInputs(): Boolean = etEmail.validateNonEmpty() && etPassword.validateNonEmpty()

    // opens RegisterActivity
    private fun goToRegisterActivity() {
        val registerIntent = Intent(this, RegisterActivity::class.java)
        startActivity(registerIntent)
    }
}
