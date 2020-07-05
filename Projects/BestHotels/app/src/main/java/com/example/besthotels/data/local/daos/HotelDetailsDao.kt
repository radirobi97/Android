package com.example.besthotels.data.local.daos


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.besthotels.data.model.HotelDetails
import kotlinx.coroutines.flow.Flow


@Dao
interface HotelDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHotelDetails(hotelDetails: HotelDetails)

    @Query("SELECT * FROM hotel_details WHERE locationId = :locationSearchId")
    fun getHotelDetailsByLocationId(locationSearchId: Int) : Flow<HotelDetails>
}