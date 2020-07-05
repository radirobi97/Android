package com.example.besthotels.data.local.daos


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.besthotels.data.model.Hotel
import kotlinx.coroutines.flow.Flow


@Dao
interface HotelsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHotels(hotels: List<Hotel>)

    @Query(
        """SELECT * FROM hotels
        WHERE locationSearchId = :locationSearchId AND checkIn = :checkIn
        AND adults = :adults AND rooms = :rooms AND nights = :nights AND
        maxPrice = :maxPrice AND hotelClass = :hotelClass AND amenityInput = :amenityInput"""
    )
    fun getAllHotelsByLocationId(
        locationSearchId: Int,
        checkIn: String,
        adults: Int,
        rooms: Int,
        nights: Int,
        maxPrice: Int,
        hotelClass: Float,
        amenityInput: String
    ): Flow<List<Hotel>>
}