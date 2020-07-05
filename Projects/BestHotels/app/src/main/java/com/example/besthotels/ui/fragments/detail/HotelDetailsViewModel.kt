package com.example.besthotels.ui.fragments.detail


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.besthotels.data.model.HotelDetails
import com.example.besthotels.data.repository.HotelDetailsRepository
import com.example.besthotels.util.State
import javax.inject.Inject


class HotelDetailsViewModel @Inject constructor(private val hotelDetailsRepository: HotelDetailsRepository) : ViewModel(){

    fun getHotelsListFromLocationId(location_id: Int) : LiveData<State<HotelDetails>> {
        return hotelDetailsRepository.getHotelDetailsListFromLocationId(location_id).asLiveData(viewModelScope.coroutineContext)
    }

}