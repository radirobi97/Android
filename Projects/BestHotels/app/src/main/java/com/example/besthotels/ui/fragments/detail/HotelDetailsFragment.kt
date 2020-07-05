package com.example.besthotels.ui.fragments.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.besthotels.R
import com.example.besthotels.data.model.HotelDetails
import com.example.besthotels.model.AmenityFilter
import com.example.besthotels.ui.adapters.AmenityRecyclerAdapter
import com.example.besthotels.util.AmenityFilterData
import com.example.besthotels.util.State
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_hotel_details.*
import javax.inject.Inject


class HotelDetailsFragment : DaggerFragment() {

    private val args: HotelDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    private val hotelDetailsViewModel by lazy {
        ViewModelProvider(this, viewModelProvider).get(HotelDetailsViewModel::class.java)
    }


    private lateinit var amenityList: MutableList<AmenityFilter>
    private val amenityFilterList = AmenityFilterData().getAmenityFilterList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hotel_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setOnClickListeners()
        initRecyclerView()

        val hotelLocationId = args.hotelLocationId
        val hotelIv = args.hotelImage
        val hotelTv = args.hotelName

        if(hotelIv != null && hotelTv != null){
            hotelDetailsIv.apply {
                transitionName = hotelIv
                Glide.with(this)
                    .load(hotelIv)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .apply( RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(this)
            }

            hotelDetailsName.apply {
                transitionName = hotelTv
                text = hotelTv
            }
        }

        showHotelDetails(hotelLocationId)
    }

    private fun initRecyclerView() {
        amenityList = mutableListOf<AmenityFilter>()
        val adapter = AmenityRecyclerAdapter(amenityList)
        rv_amenity.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_amenity.adapter = adapter
    }

    private fun showHotelDetails(hotelLocationId: Int){

        hotelDetailsViewModel.getHotelsListFromLocationId(hotelLocationId).
        observe(viewLifecycleOwner, Observer<State<HotelDetails>>{ hotelDetailsResponse ->

            when(hotelDetailsResponse) {
                is State.Loading -> {

                }
                is State.Success -> {
                    if(hotelDetailsResponse?.data != null) {
                        showProgressBar(false)

                        val response = hotelDetailsResponse.data
                        hotelDetailsAddress.text = response.address
                        hotelDetailsPhone.text = response.phone
                        hotelDetailsRanking.text = response.ranking
                        if(response.price != null){
                            hotelDetailsPrice.text = response.price
                            perNightTv.text = "per night"
                        }
                        hotelDetailsDescription.text = response.description

                        for(amenityFilter in amenityFilterList){
                            for(amenityName in response.amenities){
                                if(amenityFilter.id == amenityName.key){
                                    amenityList.add(amenityFilter)
                                }
                            }
                        }
                    }
                    else {
                        showProgressBar(true)
                    }
                }
                is State.Error -> {

                }
            }
        })
    }

    private fun showProgressBar(flag: Boolean) {
        if(flag){
            progressBarHotelDetails.show()
            addressIcon.visibility = View.GONE
            phoneIcon.visibility = View.GONE
            rankingIcon.visibility = View.GONE
            hotelDetailsSeparator.visibility = View.GONE
        }
        else {
            progressBarHotelDetails.hide()
            addressIcon.visibility = View.VISIBLE
            phoneIcon.visibility = View.VISIBLE
            rankingIcon.visibility = View.VISIBLE
            hotelDetailsSeparator.visibility = View.VISIBLE
        }
    }

    private fun setOnClickListeners(){
        hotelDetailsBackIb.setOnClickListener{
            requireView().findNavController().navigate(R.id.action_hotelDetailsFragment_to_hotelFragment)
        }
    }
}