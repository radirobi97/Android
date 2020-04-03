package com.example.diptervproto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.diptervproto.fragments.HomeFragment
import com.example.diptervproto.fragments.PredictFragment
import com.example.diptervproto.fragments.ProfileFragment
import com.example.diptervproto.room.Car
import com.example.diptervproto.room.CarRoomDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fap.setOnClickListener {
            val corotscop = CoroutineScope(Dispatchers.IO)
            val temp = CarRoomDatabase.getDatabase(application, corotscop).carDao()
            CoroutineScope(Dispatchers.IO).launch {
                temp.insertCar(Car("demo", "demo", 1432, R.drawable.car))
            }

            Log.d("HOME", "COROT SUC")
        }

        homeFragment = HomeFragment.newInstance()
        loadFragment(homeFragment)
        btmNav.setOnNavigationItemSelectedListener { setUpBottomNavigation(it) }
    }

    private fun setUpBottomNavigation(selectedItem: MenuItem): Boolean =
        when(selectedItem.itemId) {
            R.id.navHome -> {
                loadFragment(homeFragment)
                true
            }
            R.id.navPredict -> {
                loadFragment(PredictFragment.newInstance())
                true
            }
            R.id.navProfile -> {
                loadFragment(ProfileFragment.newInstance())
                true
            }
            else -> false
        }

    private fun loadFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragmentContainer, fragment, null)
            .commit()
    }
}
