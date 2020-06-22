package com.example.nagyhazi.data.sources

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nagyhazi.data.models.Car
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreDB {

    private val USERS_DB_CHILD: String = "users"
    private val CARS_DB_CHILD: String = "car"
    private val listOfCars = ArrayList<Car>()
    private val listOfCarsMutableLiveData = MutableLiveData<List<Car>>()
    private val firestoreCarsRef = FirebaseFirestore.getInstance().collection("car")
    private var carMutableLiveData = MutableLiveData<Car>()
    
    init {
        firestoreCarsRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            val cars = ArrayList<Car>()
            for(doc in querySnapshot!!){
                val car = doc.toObject(Car::class.java)
                cars.add(car)
            }
            Log.d("firestore", "listener added")
            Log.d("firestore", cars.size.toString())
            listOfCarsMutableLiveData.value = cars
        }
    }

    fun getAllCarsFromFirestore(): LiveData<List<Car>> {
        firestoreCarsRef.get()
            .addOnSuccessListener { documents ->
                for(document in documents) {
                    val car = document.toObject(Car::class.java)
                    listOfCars.add(car)
                }
                listOfCarsMutableLiveData.value = listOfCars
                Log.d("firestore", "getall added")
            }
        return listOfCarsMutableLiveData
    }
    
    
}