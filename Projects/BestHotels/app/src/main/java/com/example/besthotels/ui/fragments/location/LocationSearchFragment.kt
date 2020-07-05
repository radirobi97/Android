package com.example.besthotels.ui.fragments.location


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.besthotels.R
import com.example.besthotels.data.model.LocationSearch
import com.example.besthotels.ui.adapters.StaggeredRecyclerAdapter
import com.example.besthotels.model.Country
import com.example.besthotels.ui.shared.SharedViewModel
import com.example.besthotels.util.CountryData
import com.example.besthotels.util.State
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_location_search.*
import javax.inject.Inject


class LocationSearchFragment : DaggerFragment(), StaggeredRecyclerAdapter.OnItemClickListener {

    private var countryList : MutableList<Country> = mutableListOf()

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    private val locationSearchViewModel by lazy {
        ViewModelProvider(this, viewModelProvider).get(LocationSearchViewModel::class.java)
    }
    private val sharedViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelProvider).get(SharedViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_location_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //sharedViewModel =  ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        initAndPopulateStaggeredRecyclerView()
        initSearchView()
    }

    private fun initAndPopulateStaggeredRecyclerView() {
        countryList = CountryData().getCountryList()
        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val gridAdapter = StaggeredRecyclerAdapter(this)
        rv_photos.layoutManager = gridLayoutManager
        rv_photos.adapter = gridAdapter
        gridAdapter.submitList(countryList)
    }

    private fun initSearchView() {
        searchview_location.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query == null || query.isEmpty()){
                    Toast.makeText(context, "Enter a destination", Toast.LENGTH_SHORT).show()
                }else{
                    searchHotels(query.toLowerCase())
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun searchHotels(query: String){
        locationSearchViewModel.getLocationIdFromLocationSearch(query, "GBP").
        observe(viewLifecycleOwner, Observer<State<LocationSearch>>{ locationSearchResponse ->

            when(locationSearchResponse) {
                is State.Loading -> {
                    showProgressBar(true)
                }
                is State.Success -> {
                    showProgressBar(false)
                    sharedViewModel.setLocationSearchId(locationSearchResponse.data.locationId)
                    requireView().findNavController().navigate(R.id.action_locationSearchFragment_to_hotelFragment)
                }
                is State.Error -> {

                }
            }
        })
    }

    private fun showProgressBar(flag: Boolean) {
        if(flag){
            rv_photos.visibility = View.GONE
            pb_loading.visibility = View.VISIBLE
        }
        else {
            rv_photos.visibility = View.VISIBLE
            pb_loading.visibility = View.GONE
        }
    }

    override fun onCountryClick(countryName: String) {
        searchHotels(countryName)
    }
}