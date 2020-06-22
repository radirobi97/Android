package com.example.nagyhazi.views.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nagyhazi.R
import com.example.nagyhazi.utils.REQUEST_CODE_LOGIN_ACTIVITY
import com.example.nagyhazi.views.auth.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_login.*

/*
    This fragment displays a button which starts LoginActivity using startActivityForResult which is a method of parent (MainActivity).
 */
class LoginFragment: Fragment() {

    companion object {
        // TAG which holds the class name
        const val TAG = "LoginFragment"
        // method to instantiate a fragment
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLoginRegister.setOnClickListener { goToLoginActivity() }
    }

    // opens LoginActivity
    private fun goToLoginActivity() {
        val loginIntent = Intent(activity, LoginActivity::class.java)
        activity?.startActivityForResult(loginIntent, REQUEST_CODE_LOGIN_ACTIVITY)
    }
}