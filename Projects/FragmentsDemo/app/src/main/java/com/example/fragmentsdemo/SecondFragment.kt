package com.example.fragmentsdemo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment: Fragment(){

    private var mSecondFragmentListener: FragmentSecondListener? = null

    companion object{
        const val TAG = "SECOND_FRAGMENT"

        fun newInstance(): SecondFragment = SecondFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentSecondListener) {
            mSecondFragmentListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement FragmentSecondListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btSec.setOnClickListener {
            mSecondFragmentListener?.textSentFromFragmentB(etSec.text.toString())
        }
    }

    override fun onDetach() {
        super.onDetach()
        mSecondFragmentListener = null
    }

    fun setText(text: String){
        tvSec.text = text
    }

    interface FragmentSecondListener {
        fun textSentFromFragmentB(input: String)
    }
}