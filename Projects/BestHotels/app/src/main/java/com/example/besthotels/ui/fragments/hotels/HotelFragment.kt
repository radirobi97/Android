package com.example.besthotels.ui.fragments.hotels


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.besthotels.R
import com.example.besthotels.data.model.Hotel
import com.example.besthotels.ui.adapters.HotelRecyclerAdapter
import com.example.besthotels.ui.shared.SharedViewModel
import com.example.besthotels.util.State
import com.google.android.material.textview.MaterialTextView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_hotel.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class HotelFragment : DaggerFragment(), HotelRecyclerAdapter.OnItemClickListener {

    private var hotelList: MutableList<Hotel> = mutableListOf()


    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    private val hotelsViewModel by lazy {
        ViewModelProvider(this, viewModelProvider).get(HotelsViewModel::class.java)
    }
    private val sharedViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelProvider).get(SharedViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hotel, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setOnClickListeners()


        if(sharedViewModel.locationId.value != null){
            val locationId = sharedViewModel.locationId.value!!
            listHotels(locationId)
        }
    }

    private fun setOnClickListeners() {
        ib_back.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_hotelFragment_to_locationSearchFragment)
        }
        ib_filter.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_hotelFragment_to_hotelFilterFragment)
        }
    }

    private fun listHotels(getLocationId: Int){
        createDefaultFilterValues()
        val checkIn = sharedViewModel.checkIn.value
        val adults = sharedViewModel.adults.value
        val rooms = sharedViewModel.rooms.value
        val nights = sharedViewModel.nights.value
        val maxPrice = sharedViewModel.maxPrice.value
        val hotelClass =  sharedViewModel.hotelClass.value
        val amenities = sharedViewModel.amenities.value

        val adapter = HotelRecyclerAdapter(this)
        rv_hotels.adapter = adapter
        rv_hotels.layoutManager = LinearLayoutManager (context)

        hotelsViewModel.getHotelsListFromLocationId(getLocationId, checkIn!!, adults!!, rooms!!, nights!!,
            maxPrice!!, hotelClass!!, amenities!!)
            .observe(viewLifecycleOwner, Observer<State<List<Hotel>>>{ hotelResponse ->
                when(hotelResponse) {
                    is State.Loading -> {
                        showProgressBar(true)
                    }
                    is State.Success -> {
                        showProgressBar(false)

                        for (i in hotelResponse.data) {
                            if(i.photo != null){
                                hotelList.add(
                                    Hotel(i.locationId, i.locationSearchId, i.checkIn, i.adults, i.rooms, i.nights,
                                        i.maxPrice, i.hotelClass, i.amenityInput,
                                        i.name, i.latitude, i.longitude, i.numReviews?:0,
                                        i.ranking?: "", i.rating?:0f, i.priceLevel?: "", i.price?: "", i.photo)
                                )
                            }
                        }
                        adapter.submitList(hotelList)
                    }
                    is State.Error -> {
                    }
                }
            })
    }

    private fun createDefaultFilterValues() {
        if(sharedViewModel.checkIn.value == null){
            val formatterCurrDateTv  =  SimpleDateFormat("yyyy-MM-dd")
            sharedViewModel.setCheckIn(formatterCurrDateTv.format(Date()).toString())
        }

        if(sharedViewModel.adults.value == null){
            sharedViewModel.setAdults(1)
        }

        if(sharedViewModel.rooms.value == null){
            sharedViewModel.setRooms(1)
        }

        if(sharedViewModel.nights.value == null){
            sharedViewModel.setNights(1)
        }

        if(sharedViewModel.maxPrice.value == null){
            sharedViewModel.setMaxPrice(30)
        }

        if(sharedViewModel.hotelClass.value == null){
            sharedViewModel.setHotelClass(3.0f)
        }

        if(sharedViewModel.amenities.value == null){
            sharedViewModel.setAmenities("")
        }
    }

    private fun showProgressBar(flag: Boolean) {
        if(flag){
            rv_hotels.visibility = View.GONE
            pb_loading_hotels.visibility = View.VISIBLE
        }
        else {
            rv_hotels.visibility = View.VISIBLE
            pb_loading_hotels.visibility = View.GONE
        }
    }

    override fun onHotelClick(hotel: Hotel, iv: AppCompatImageView, tv: MaterialTextView) {
        val extras = FragmentNavigatorExtras(
            iv to hotel.photo!!.images.original.url,
            tv to hotel.name
        )
        val action = HotelFragmentDirections.actionHotelFragmentToHotelDetailsFragment(
            hotelLocationId = hotel.locationId,
            hotelImage = hotel.photo!!.images.original.url,
            hotelName = hotel.name
        )
        requireView().findNavController().navigate(action, extras)
    }
}