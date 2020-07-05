package com.example.besthotels.util


import com.example.besthotels.R
import com.example.besthotels.model.AmenityFilter


class AmenityFilterData{

    private val amenityFilterList: MutableList<AmenityFilter> = mutableListOf()

    private fun populateAmenityFilter(){
        amenityFilterList.add(AmenityFilter("free_internet", "Free Internet", R.drawable.ic_wifi))
        amenityFilterList.add(AmenityFilter("free_parking", "Free Parking", R.drawable.ic_parking))
        amenityFilterList.add(AmenityFilter("room_service", "Room Service", R.drawable.ic_room_service))
        amenityFilterList.add(AmenityFilter("fitness_center", "Fitness Center", R.drawable.ic_fitness))
        amenityFilterList.add(AmenityFilter("swimming_pool", "Swimming Pool", R.drawable.ic_pool))
        amenityFilterList.add(AmenityFilter("pets_allowed", "Pets Allowed", R.drawable.ic_pet))
        amenityFilterList.add(AmenityFilter("casino", "Casino", R.drawable.ic_casino))
        amenityFilterList.add(AmenityFilter("restaurant", "Restaurant", R.drawable.ic_restaurant))
        amenityFilterList.add(AmenityFilter("laundry", "Laundry", R.drawable.ic_laundry))
        amenityFilterList.add(AmenityFilter("spa", "Spa", R.drawable.ic_spa))
        amenityFilterList.add(AmenityFilter("non_smoking_hotel", "Non-Smoking Hotel", R.drawable.ic_non_smoke))
    }

    fun getAmenityFilterList(): MutableList<AmenityFilter>{
        populateAmenityFilter()
        return amenityFilterList
    }
}