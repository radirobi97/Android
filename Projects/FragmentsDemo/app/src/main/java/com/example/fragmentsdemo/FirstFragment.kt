package com.example.fragmentsdemo

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment: Fragment(){

    private var mFirstFragmentListener: FragmentFirstListener? = null

    companion object{
        const val TAG = "FIRST_FRAGMENT"

        fun newInstance(): FirstFragment = FirstFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentFirstListener) {
            mFirstFragmentListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement FragmentFirstListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btFirst.setOnClickListener {
            mFirstFragmentListener?.textSentFromFragmentA(etFirst.text.toString())
        }
    }

    override fun onDetach() {
        super.onDetach()
        mFirstFragmentListener = null
    }

    fun setText(text: String){
        tvFirst.text = text
    }

    interface FragmentFirstListener{
        fun textSentFromFragmentA(input: String)
    }
}