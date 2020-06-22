package com.example.nagyhazi.repositories

import androidx.lifecycle.LiveData
import com.example.nagyhazi.room.Car
import com.example.nagyhazi.room.CarDao

class CarRepository(private val carDao: CarDao) {

    val allCars: LiveData<List<Car>> = carDao.getCars()

    suspend fun insert(car: Car) {
        carDao.insertCar(car)
    }
}