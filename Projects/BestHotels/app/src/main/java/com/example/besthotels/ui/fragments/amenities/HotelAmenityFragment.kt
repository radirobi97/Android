package com.example.besthotels.ui.fragments.amenities


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.besthotels.R
import com.example.besthotels.ui.shared.SharedViewModel
import kotlinx.android.synthetic.main.fragment_hotel_amenity.*


class HotelAmenityFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hotel_amenity, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel =  ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        persistCheckBoxes()
        setOnClickListener()
    }

    private fun persistCheckBoxes(){

        if(sharedViewModel.amenities.value != null){
            val amenityString = sharedViewModel.amenities.value
            val amenities: List<String> = amenityString!!.split(",").map { it.trim() }
            amenities.forEach { amenity->
                when (amenity) {
                    "free_internet" -> checkbox_wifi.isChecked = true
                    "free_parking" -> checkbox_parking.isChecked = true
                    "room_service" -> checkbox_room.isChecked = true
                    "fitness_center" -> checkbox_fitness.isChecked = true
                    "swimming_pool" -> checkbox_swimming.isChecked = true
                    "pets_allowed" -> checkbox_pets.isChecked = true
                    "casino" -> checkbox_casino.isChecked = true
                    "restaurant" -> checkbox_restaurant.isChecked = true
                    "laundry" -> checkbox_laundry.isChecked = true
                    "spa" -> checkbox_spa.isChecked = true
                    "non_smoking_hotel" -> checkbox_non_smoke.isChecked = true
                }
            }
        }
    }

    private fun setOnClickListener() {
        btn_amenity_done.setOnClickListener {
            val amenities = checkAmenities()
            sharedViewModel.setAmenities(amenities)
            requireView().findNavController().navigate(R.id.action_hotelAmenityFragment_to_hotelFilterFragment)
        }
    }

    private fun checkAmenities(): String{

        val amenities = mutableListOf<String>()
        if(checkbox_wifi.isChecked)
            amenities.add("free_internet")
        if(checkbox_parking.isChecked)
            amenities.add( "free_parking")
        if(checkbox_room.isChecked)
            amenities.add( "room_service")
        if(checkbox_fitness.isChecked)
            amenities.add( "fitness_center")
        if(checkbox_swimming.isChecked)
            amenities.add( "swimming_pool")
        if(checkbox_pets.isChecked)
            amenities.add( "pets_allowed")
        if(checkbox_casino.isChecked)
            amenities.add( "casino")
        if(checkbox_restaurant.isChecked)
            amenities.add( "restaurant")
        if(checkbox_laundry.isChecked)
            amenities.add( "laundry")
        if(checkbox_spa.isChecked)
            amenities.add( "spa")
        if(checkbox_non_smoke.isChecked)
            amenities.add( "non_smoking_hotel")

        return amenities.joinToString(",")
    }
}