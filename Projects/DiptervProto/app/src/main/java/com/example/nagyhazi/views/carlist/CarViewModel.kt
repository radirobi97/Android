package com.example.nagyhazi.views.carlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.nagyhazi.data.models.Car
import com.example.nagyhazi.data.sources.FirebaseDB

class CarViewModel(application: Application): AndroidViewModel(application) {

    //private val carRepository: CarRepository
    //private val firestoreDB = FirestoreDB()
    private val firebaseDB = FirebaseDB()
    var allCars: LiveData<List<Car>>
    var totalPageSize: LiveData<Int>

    init {
        //val carDao = CarRoomDatabase.getDatabase(application, viewModelScope).carDao()
        //carRepository = CarRepository(carDao)
        //allCars = firestoreDB.getAllCarsFromFirestore()

        //allCars = firebaseDB.getAllCarsFromDatabase()
        allCars = firebaseDB.getFirstCars()
        firebaseDB.calcTotalPages()
        totalPageSize = firebaseDB.databaseChangeListener()
    }

    // loads more cars into recyclerview
    fun addMoreCars() {
        firebaseDB.addMoreCars()
    }

    fun getCarsByType(type: String): LiveData<List<Car>> {
        return firebaseDB.getCarsByType(type)
    }

    fun getCurrentPage(): Int {
        return firebaseDB.getCurrentPage()
    }

    fun getTotalPageNum(): Int {
        return firebaseDB.getTotalPageNum()
    }

}