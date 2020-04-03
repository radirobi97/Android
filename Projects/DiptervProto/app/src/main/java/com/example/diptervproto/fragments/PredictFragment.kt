package com.example.diptervproto.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.diptervproto.R
import kotlinx.android.synthetic.main.fragment_predict.*

class PredictFragment: Fragment() {

    companion object {
        //TAG which holds the class name
        const val TAG = "PredictFragment"

        fun newInstance():PredictFragment{
            val fragment=PredictFragment()
            return  fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_predict, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnPredict.setOnClickListener {
            tvPredict.text = (tvPredict.text.toString().toInt() + 1).toString()
        }
    }
}