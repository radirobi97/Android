package com.example.carpricepredictor.data

import android.util.Log
import com.example.carpricepredictor.R

class FakeDataSource {

    private val images = arrayListOf<Int>(R.drawable.ford, R.drawable.mazda, R.drawable.skoda)
    private val brands = arrayListOf<String>("mazda", "ford", "skoda")
    private val types  = arrayListOf<String>("octavia", "avensis", "i431")
    private val prices = arrayListOf<Int>(1000000, 2000000, 3000000)


    fun getListOfData(): List<Car> {

        var carList = ArrayList<Car>()

        for (i in 0..12){
            carList.add(createNewCar())
        }

        Log.d("getlsitofdatafinished", "finish")
        return carList
    }

    private fun createNewCar(): Car {

        var imgRand: Int = (0..2).random()
        var brandRand: Int = (0..2).random()
        var typeRand: Int = (0..2).random()
        var priceRand: Int = (0..2).random()

        return Car(images[imgRand], brands[brandRand], types[typeRand], prices[priceRand])
    }

}