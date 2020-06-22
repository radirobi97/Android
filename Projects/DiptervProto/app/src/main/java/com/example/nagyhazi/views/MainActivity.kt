package com.example.nagyhazi.views

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.nagyhazi.R
import com.example.nagyhazi.services.LogoutService
import com.example.nagyhazi.views.carlist.CarListFragment
import com.example.nagyhazi.views.profile.LoginFragment
import com.example.nagyhazi.views.search.SearchFragment
import com.example.nagyhazi.views.profile.ProfileFragment
import com.example.nagyhazi.utils.REQUEST_CODE_LOGIN_ACTIVITY
import com.example.nagyhazi.utils.REQUEST_CODE_UPLOAD_ACTIVITY
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


/*
    This activity is responsible for showing and hiding fragments based on click events on bottom navigation component.
 */
class MainActivity : AppCompatActivity() {

    // we store the current selected fragment in the bundle with this name if any configuration changes happen
    val SELECTED_FRAGMENT = "selected_fragment"
    // firebase instance to check whether the user is authenticated or not
    private val firebaseAuth = FirebaseAuth.getInstance()
    // fragmentManager handles the fragment transactions
    private val fragmentManager = supportFragmentManager
    // fragment instances
    private var carListFragment = CarListFragment.newInstance()
    private var loginFragment = LoginFragment.newInstance()
    private var profileFragment = ProfileFragment.newInstance()
    private var predictFragment = SearchFragment.newInstance()
    // activeFragment is the selected fragment, by default carListFragment
    private var activeFragment: Fragment = carListFragment

    /*
        * restores states from Bundle if needed
        * defines events to elements of bottom navigation component
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startLogoutService()
        setContentView(R.layout.activity_main)
        btmNav.setOnNavigationItemSelectedListener { menuId -> setUpBottomNavigation(menuId) }
        if (savedInstanceState == null) {
            // shows and hides the proper fragments
            setDefaultFragmentToBottomNav()
        }
        else {
            // fragments can be extracted from fragment manager after configuration changes
            carListFragment = fragmentManager?.findFragmentByTag(CarListFragment.TAG) as CarListFragment
            predictFragment = fragmentManager.findFragmentByTag(SearchFragment.TAG) as SearchFragment
            loginFragment = fragmentManager.findFragmentByTag(LoginFragment.TAG) as LoginFragment
            profileFragment = fragmentManager.findFragmentByTag(ProfileFragment.TAG) as ProfileFragment
            // selects the fragment which was the active before configuration changes happened
            btmNav.selectedItemId = savedInstanceState.getInt(SELECTED_FRAGMENT)
        }
    }

    private fun startLogoutService() {
        val serviceIntent = Intent(this, LogoutService::class.java)
        startService(serviceIntent)
    }

    /*
        * handles click events on bottom navigation component
        * hides the previous fragment
        * assigns the new value to the activeFragment
     */
    private fun setUpBottomNavigation(selectedItem: MenuItem): Boolean =
        when (selectedItem.itemId) {
            R.id.navHome -> {
                fragmentManager.beginTransaction().hide(activeFragment).show(carListFragment).commit()
                activeFragment = carListFragment
                true
            }
            R.id.navPredict -> {
                fragmentManager.beginTransaction().hide(activeFragment).show(predictFragment).commit()
                activeFragment = predictFragment
                true
            }
            R.id.navProfile -> {
                if (checkIfUserAuthenticated()) {
                    fragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit()
                    activeFragment = profileFragment
                } else {
                    fragmentManager.beginTransaction().hide(activeFragment).show(loginFragment).commit()
                    activeFragment = loginFragment
                }
                true
            }
            else -> false
        }


    // checks whether user is signed in Firebase or not
    private fun checkIfUserAuthenticated(): Boolean = firebaseAuth.currentUser != null

    /*
        * adds all of the fragments to the container
        * hides each of them except carListFragment
        * carListFragment is visible by default
     */
    private fun setDefaultFragmentToBottomNav() {
        fragmentManager.beginTransaction().add(R.id.fragmentContainer, predictFragment, SearchFragment.TAG).hide(predictFragment).commit()
        fragmentManager.beginTransaction().add(R.id.fragmentContainer, loginFragment, LoginFragment.TAG).hide(loginFragment).commit()
        fragmentManager.beginTransaction().add(R.id.fragmentContainer, profileFragment, ProfileFragment.TAG).hide(profileFragment).commit()
        fragmentManager.beginTransaction().add(R.id.fragmentContainer, carListFragment, CarListFragment.TAG).commit()
    }

    /*
        * handles result of started activity. Activity was started by LoginFragment but fragments has no onActivityResult method
        * simulates clicking on navProfile to update the content of fragmentContainer
    */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("SIMULATION", "startActivityForResult has ended")
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_LOGIN_ACTIVITY) {
            if (resultCode == Activity.RESULT_CANCELED) {
                btmNav.selectedItemId = R.id.navProfile
            }
        }
        if (requestCode == REQUEST_CODE_UPLOAD_ACTIVITY) {
            if(resultCode == Activity.RESULT_CANCELED) {
                btmNav.selectedItemId = R.id.navProfile
            }
        }
    }

    /*
        * hides all fragments
        * saves selected fragment in bottom navigation component
     */
    override fun onSaveInstanceState(outState: Bundle) {
        fragmentManager.beginTransaction().hide(predictFragment).commit()
        fragmentManager.beginTransaction().hide(loginFragment).commit()
        fragmentManager.beginTransaction().hide(profileFragment).commit()
        fragmentManager.beginTransaction().hide(carListFragment).commit()
        outState.putInt(SELECTED_FRAGMENT, btmNav.selectedItemId)
        super.onSaveInstanceState(outState)
    }
}
