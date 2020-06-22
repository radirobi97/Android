package com.example.diptervproto.repositories

import androidx.lifecycle.LiveData
import com.example.diptervproto.room.Car
import com.example.diptervproto.room.CarDao

class CarRepository(private val carDao: CarDao) {

    val allCars: LiveData<List<Car>> = carDao.getCars()

    suspend fun insert(car: Car) {
        carDao.insertCar(car)
    }
}