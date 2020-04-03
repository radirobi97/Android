package com.example.diptervproto.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.diptervproto.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment(): Fragment() {


    companion object {
        //TAG which holds the class name
        const val TAG = "ProfileFragment"

        fun newInstance():ProfileFragment{
            val fragment=ProfileFragment()
            return  fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnProfile.setOnClickListener {
            tvProfile.text = (tvProfile.text.toString().toInt() + 1).toString()
        }
    }
}