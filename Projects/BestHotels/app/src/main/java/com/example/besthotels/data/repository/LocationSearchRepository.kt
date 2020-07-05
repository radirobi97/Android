package com.example.besthotels.data.repository


import android.content.SharedPreferences
import com.example.besthotels.data.local.daos.LocationSearchDao
import com.example.besthotels.data.model.LocationSearch
import com.example.besthotels.data.model.LocationSearchResponse
import com.example.besthotels.data.remote.TripAdvisorService
import com.example.besthotels.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class LocationSearchRepository @Inject constructor(
    private val sharedPref: SharedPreferences,
    private val apiInterface: TripAdvisorService,
    private val locationSearchDao: LocationSearchDao
) {

    companion object {
        private val LOCATION_EXPIRY_IN_MILLIS = TimeUnit.HOURS.toMillis(1L)
        private const val KEY_LAST_SYNCED = "last_synced"
    }

    fun getLocationIdFromLocationSearch(query: String, currency: String): Flow<State<LocationSearch>> {
        return object : NetworkBoundRepository<LocationSearch, LocationSearchResponse>() {

            override fun fetchFromLocal(): Flow<LocationSearch> = locationSearchDao.getLocationByName(query)

            override suspend fun fetchFromRemote(): Response<LocationSearchResponse> = apiInterface.getLocationIdFromLocationSearch(query, currency)

            override fun saveRemoteData(data: LocationSearchResponse) {


                for(data in data.data){
                    if(data.resultType == "geos"){
                        val resultObj = data.resultObject
                        val locationSearch = LocationSearch(resultObj!!.locationId, query, resultObj.latitude, resultObj.longitude)
                        locationSearchDao.insertLocation(locationSearch)
                    }
                }

                val editor = sharedPref.edit()
                editor.putLong(KEY_LAST_SYNCED, System.currentTimeMillis())
                editor.apply()
            }

            override fun shouldFetchFromRemote(data: LocationSearch?): Boolean {
                val lastSynced = sharedPref.getLong(KEY_LAST_SYNCED, -1)
                return lastSynced == -1L || data == null || isExpired(lastSynced)
            }

        }.asFlow().flowOn(Dispatchers.IO)
    }


    private fun isExpired(lastSynced: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastSynced) >= LOCATION_EXPIRY_IN_MILLIS
    }
}