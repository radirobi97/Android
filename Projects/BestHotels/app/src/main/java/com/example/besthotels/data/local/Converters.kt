package com.example.besthotels.data.local


import androidx.room.TypeConverter
import com.example.besthotels.data.model.Amenity
import com.example.besthotels.data.model.Images
import com.example.besthotels.data.model.Original
import com.example.besthotels.data.model.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {

    @TypeConverter
    fun photofromString(value: String): Photo? {
        val photo =  object: TypeToken<Photo?>(){}.type
        return  Gson().fromJson(value, photo)
    }


    @TypeConverter
    fun stringfromPhoto(photo: Photo?): String {
        val gson =  Gson()
        return gson.toJson(photo)
    }



    @TypeConverter
    fun imagesfromString(value: String): Images? {
        val images =  object: TypeToken<Images?>(){}.type
        return  Gson().fromJson(value, images)
    }

    @TypeConverter
    fun stringfromImages(images: Images?): String {
        val gson =  Gson()
        return gson.toJson(images)
    }


    @TypeConverter
    fun originalfromString(value: String): Original? {
        val original =  object: TypeToken<Original?>(){}.type
        return  Gson().fromJson(value, original)
    }


    @TypeConverter
    fun stringfromOriginal(original: Original?): String {
        val gson =  Gson()
        return gson.toJson(original)
    }

    @TypeConverter
    fun amenitiesfromString(value: String): List<Amenity>? {
        val amenities =  object: TypeToken<List<Amenity>?>(){}.type
        return  Gson().fromJson(value, amenities)
    }


    @TypeConverter
    fun stringfromAmenities(amenities:  List<Amenity>?): String {
        val gson =  Gson()
        return gson.toJson(amenities)
    }
}