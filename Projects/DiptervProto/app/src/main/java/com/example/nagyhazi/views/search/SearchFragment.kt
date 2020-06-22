package com.example.nagyhazi.views.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nagyhazi.R
import kotlinx.android.synthetic.main.fragment_search.*
import java.text.DecimalFormat

class SearchFragment: Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var searchViewModel: SearchViewModel

    companion object {
        //TAG which holds the class name
        const val TAG = "PredictFragment"
        //Method to instantiate a fragment
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        searchViewModel = ViewModelProvider(activity!!).get(SearchViewModel::class.java)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnChangeListenerToSeekbar()
        fillUpSpinner()
        initSearchViewModel()
    }

    private fun setOnChangeListenerToSeekbar() {
        rangeSeekBar.setOnRangeSeekbarChangeListener { minValue, maxValue ->
            val decim = DecimalFormat("#,###,###")
            tvRangeMin.text = decim.format(minValue)
            tvRangeMax.text = decim.format(maxValue)
        }
    }

    private fun fillUpSpinner() {
        ArrayAdapter.createFromResource(activity!!.applicationContext, R.array.car_types, android.R.layout.simple_spinner_item)
            .also { adapter ->
                // sorts values alphabetically
                adapter.sort { first, second ->  first.toString().compareTo(second.toString())}
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerType.adapter = adapter
            }
        spinnerType.onItemSelectedListener = this
    }

    private fun initSearchViewModel() {
        searchViewModel.initSpinnerLiveData()
        // populates second spinner based on the first spinner
        val modelsByTypeObserver = Observer<ArrayList<String>> { models ->
            val newAdapter = ArrayAdapter(activity!!.applicationContext, android.R.layout.simple_spinner_item, models)
            newAdapter.sort { first, second -> first.toString().compareTo(second.toString()) }
            newAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerModel.adapter = newAdapter
        }
        searchViewModel.modelsByType.observe(viewLifecycleOwner, modelsByTypeObserver)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val selectedItem = p0?.getItemAtPosition(p2).toString()
        searchViewModel.getModelsByType(selectedItem)
    }

}