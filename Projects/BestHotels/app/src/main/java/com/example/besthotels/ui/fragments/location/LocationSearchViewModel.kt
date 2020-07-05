package com.example.besthotels.ui.fragments.location


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.besthotels.data.model.LocationSearch
import com.example.besthotels.data.repository.LocationSearchRepository
import com.example.besthotels.util.State


import javax.inject.Inject


class LocationSearchViewModel @Inject constructor(private val locationSearchRepository: LocationSearchRepository) : ViewModel(){

    fun getLocationIdFromLocationSearch(query: String, currency: String) : LiveData<State<LocationSearch>> {
        return locationSearchRepository.getLocationIdFromLocationSearch(query, currency).asLiveData(viewModelScope.coroutineContext)
    }
}