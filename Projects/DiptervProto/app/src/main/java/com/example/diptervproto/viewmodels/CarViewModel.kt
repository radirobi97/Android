package com.example.diptervproto.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.diptervproto.repositories.CarRepository
import com.example.diptervproto.room.Car
import com.example.diptervproto.room.CarRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarViewModel(application: Application): AndroidViewModel(application) {

    public val carRepository: CarRepository
    val allCars: LiveData<List<Car>>

    init {
        Log.d("HOME", "view model init block")
        val carDao = CarRoomDatabase.getDatabase(application, viewModelScope).carDao()
        carRepository = CarRepository(carDao)
        allCars = carDao.getCars()
        viewModelScope.launch {
            Log.d("HOME", "INIT SIZE: ")
            Log.d("HOME", carDao.getDaoSize().toString())
        }
    }

    fun insertCart(car: Car) = viewModelScope.launch(Dispatchers.IO) {
        carRepository.insert(car)
    }
}