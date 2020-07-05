package com.example.besthotels.data.repository

import android.content.SharedPreferences
import com.example.besthotels.data.local.daos.HotelDetailsDao
import com.example.besthotels.data.model.HotelDetails
import com.example.besthotels.data.model.HotelDetailsResponse
import com.example.besthotels.data.remote.TripAdvisorService
import com.example.besthotels.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HotelDetailsRepository @Inject constructor(
    private val sharedPref: SharedPreferences,
    private val apiInterface: TripAdvisorService,
    private val hotelDetailsDao: HotelDetailsDao
) {

    companion object {
        private val DETAILS_EXPIRY_IN_MILLIS = TimeUnit.HOURS.toMillis(1L)
        private const val KEY_LAST_SYNCED = "last_synced"
    }

    fun getHotelDetailsListFromLocationId(locationId: Int): Flow<State<HotelDetails>> {
        return object : NetworkBoundRepository<HotelDetails, HotelDetailsResponse>() {

            override fun fetchFromLocal(): Flow<HotelDetails> = hotelDetailsDao.getHotelDetailsByLocationId(locationId)

            override suspend fun fetchFromRemote(): Response<HotelDetailsResponse> {
               return apiInterface.getHotelDetailsListFromLocationId(locationId)
            }

            override fun saveRemoteData(data: HotelDetailsResponse) {

                hotelDetailsDao.insertHotelDetails(data.data[0])
                val editor = sharedPref.edit()
                editor.putLong(KEY_LAST_SYNCED, System.currentTimeMillis())
                editor.apply()
            }

            override fun shouldFetchFromRemote(data: HotelDetails?): Boolean {
                val lastSynced = sharedPref.getLong(KEY_LAST_SYNCED, -1)
                return lastSynced == -1L || data == null || isExpired(lastSynced)
            }

        }.asFlow().flowOn(Dispatchers.IO)
    }


    private fun isExpired(lastSynced: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastSynced) >= DETAILS_EXPIRY_IN_MILLIS
    }
}