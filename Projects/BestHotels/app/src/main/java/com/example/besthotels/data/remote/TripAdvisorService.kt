package com.example.besthotels.data.remote


import com.example.besthotels.data.model.HotelDetailsResponse
import com.example.besthotels.data.model.HotelResponse
import com.example.besthotels.data.model.LocationSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface TripAdvisorService {

    @GET("locations/search?sort=relevance")
    suspend fun getLocationIdFromLocationSearch(
        @Query("query") query: String,
        @Query("currency") currency: String
    ): Response<LocationSearchResponse>

    @GET("hotels/list")
    suspend fun getHotelsListFromLocationId(
        @Query("location_id") location_id: Int,
        @Query("checkin") checkInDate: String,
        @Query("adults") numberOfAdults: Int,
        @Query("rooms") numberOfRooms: Int,
        @Query("nights") numberOfNights: Int,
        @Query("pricesmax") maxPrice: Int,
        @Query("hotel_class") hotelClass: Float,
        @Query("amenities") amenityInput: String
    ): Response<HotelResponse>

    @GET("hotels/get-details")
    suspend fun getHotelDetailsListFromLocationId(
        @Query("location_id") locationId: Int
    ): Response<HotelDetailsResponse>

}