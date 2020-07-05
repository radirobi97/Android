package com.example.besthotels.ui.fragments.hotels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.besthotels.data.model.Hotel
import com.example.besthotels.data.repository.HotelRepository
import com.example.besthotels.util.State
import javax.inject.Inject


class HotelsViewModel @Inject constructor(private val hotelsRepository: HotelRepository): ViewModel(){

    fun getHotelsListFromLocationId(locationId: Int, checkInData: String,
                                    numberOfAdults: Int, numberOfRooms :Int, numberOfNights: Int,
                                    maxPrice: Int, hotelClass: Float, amenities: String) : LiveData<State<List<Hotel>>> {
        return hotelsRepository.getHotelsListFromLocationId(locationId, checkInData, numberOfAdults, numberOfRooms,
            numberOfNights, maxPrice,hotelClass,amenities).asLiveData(viewModelScope.coroutineContext)
    }
}