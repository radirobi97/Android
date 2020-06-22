package com.example.nagyhazi.data.models
import com.google.firebase.database.Exclude

data class Car(var carId: String="",
               var type: String="",
               var model: String="",
               var price: String="",
               var imgUrl: String="",
               @get:Exclude var isSuccess: Boolean = false)