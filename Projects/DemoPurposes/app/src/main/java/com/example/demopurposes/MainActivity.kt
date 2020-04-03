package com.example.demopurposes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.bottomNavHome -> {
                val homeFragment = HomeFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, homeFragment, "HOME_FRAGMENT")
                    .commit()
                true
            }
            R.id.bottomNavPredict -> {
                val predictFragment = PredictFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, predictFragment, "PREDICT_FRAGMENT")
                    .commit()
                true
            }
            R.id.bottomNavProfile -> {
                val profileFragment = ProfileFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, profileFragment, "PROFILE_FRAGMENT")
                    .commit()
                true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavBar : BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
