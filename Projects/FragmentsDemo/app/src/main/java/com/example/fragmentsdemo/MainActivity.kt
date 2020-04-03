package com.example.fragmentsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FirstFragment.FragmentFirstListener, SecondFragment.FragmentSecondListener{

    var mSecondFragment: SecondFragment? = null
    var mFirstFragment: FirstFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btStartFragment.setOnClickListener {
            Toast.makeText(this, "was clicked", Toast.LENGTH_SHORT).show()
            var ft = supportFragmentManager.beginTransaction()
            mFirstFragment = FirstFragment()
            ft.replace(R.id.fragmentAContainer, mFirstFragment as Fragment, FirstFragment.TAG).commit()

            ft = supportFragmentManager.beginTransaction()
            mSecondFragment = SecondFragment()
            ft.replace(R.id.fragmentBContainer, mSecondFragment as Fragment, SecondFragment.TAG).commit()
        }
    }

    override fun textSentFromFragmentA(input: String) {
        mSecondFragment?.setText(input)
    }

    override fun textSentFromFragmentB(input: String) {
        mFirstFragment?.setText(input)
    }
}
