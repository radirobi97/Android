package com.example.nagyhazi.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_table")
data class Car( var carType: String, var carModel: String, var carPrice: Int, var carImage: Int){

    @PrimaryKey(autoGenerate = true)
    var carId: Int = 0
}