package com.example.besthotels.data.repository


import android.content.SharedPreferences
import com.example.besthotels.data.local.daos.HotelsDao
import com.example.besthotels.data.model.Hotel
import com.example.besthotels.data.model.HotelResponse
import com.example.besthotels.data.remote.TripAdvisorService
import com.example.besthotels.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class HotelRepository @Inject constructor(
    private val sharedPref: SharedPreferences,
    private val apiInterface: TripAdvisorService,
    private val hotelsDao: HotelsDao
) {

    companion object {
        private val HOTEL_EXPIRY_IN_MILLIS = TimeUnit.HOURS.toMillis(1L)
        private const val KEY_LAST_SYNCED = "last_synced"
    }

    fun getHotelsListFromLocationId(
        locationId: Int, checkInDate: String,
        numberOfAdults: Int, numberOfRooms: Int,
        numberOfNights: Int, maxPrice: Int,
        hotelClass: Float, amenityInput: String
    ): Flow<State<List<Hotel>>> {
        return object : NetworkBoundRepository<List<Hotel>, HotelResponse>() {

            override fun fetchFromLocal(): Flow<List<Hotel>> {
                val temp = hotelsDao.getAllHotelsByLocationId(
                    locationId, checkInDate, numberOfAdults,
                    numberOfRooms, numberOfNights, maxPrice,
                    hotelClass, amenityInput
                )
                return temp
            }

            override suspend fun fetchFromRemote(): Response<HotelResponse> {
                val temp = apiInterface.getHotelsListFromLocationId(
                    locationId, checkInDate, numberOfAdults, numberOfRooms, numberOfNights, maxPrice, hotelClass, amenityInput
                )
                return temp
            }

            override fun saveRemoteData(data: HotelResponse) {

                val hotelList: ArrayList<Hotel> = arrayListOf()
                for(data in data.data){
                    val hotel = Hotel(data.locationId, locationId, checkInDate,
                        numberOfAdults, numberOfRooms, numberOfNights,
                        maxPrice, hotelClass, amenityInput,
                        data.name, data.latitude, data.longitude, data.numReviews, data.ranking, data.rating,
                        data.priceLevel, data.price, data.photo
                    )
                    hotelList.add(hotel)
                }
                hotelsDao.insertHotels(hotelList)

                val editor = sharedPref.edit()
                editor.putLong(KEY_LAST_SYNCED, System.currentTimeMillis())
                editor.apply()
            }

            override fun shouldFetchFromRemote(data: List<Hotel>?): Boolean {
                val lastSynced = sharedPref.getLong(KEY_LAST_SYNCED, -1)
                return lastSynced == -1L || data.isNullOrEmpty() || isExpired(lastSynced)
            }

        }.asFlow().flowOn(Dispatchers.IO)
    }


    private fun isExpired(lastSynced: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastSynced) >= HOTEL_EXPIRY_IN_MILLIS
    }
}