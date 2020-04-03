package com.example.diptervproto.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.diptervproto.R
import com.example.diptervproto.adapter.CarListRecyclerViewAdapter
import com.example.diptervproto.room.Car
import com.example.diptervproto.viewmodels.CarViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment(): Fragment(){

    private lateinit var mCarListRecyclerViewAdapter: CarListRecyclerViewAdapter
    private lateinit var carViewModel: CarViewModel

    companion object {
        //TAG which holds the class name
        const val TAG = "HomeFragment"

        fun newInstance():HomeFragment{
            val fragment=HomeFragment()
            return  fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        carViewModel = ViewModelProvider(activity!!).get(CarViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        mCarListRecyclerViewAdapter = CarListRecyclerViewAdapter()
        recyclerCarList.adapter = mCarListRecyclerViewAdapter

        val carObserver = Observer<List<Car>> {
           // mCarListRecyclerViewAdapter.submitList(it)
            mCarListRecyclerViewAdapter.setCars(it)
            Log.d("HOME", "ADAT VALTOZAS")
        }

        carViewModel.allCars.observe(viewLifecycleOwner, carObserver)


    }


}