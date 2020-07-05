package com.example.besthotels.di.modules

import android.content.SharedPreferences
import com.example.besthotels.data.local.daos.HotelDetailsDao
import com.example.besthotels.data.local.daos.HotelsDao
import com.example.besthotels.data.local.daos.LocationSearchDao
import com.example.besthotels.data.remote.TripAdvisorService
import com.example.besthotels.data.repository.HotelDetailsRepository
import com.example.besthotels.data.repository.HotelRepository
import com.example.besthotels.data.repository.LocationSearchRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideHotelDetailsRepository(
        sharedPref: SharedPreferences,
        apiInterface: TripAdvisorService,
        hotelDetailsDao: HotelDetailsDao
    ): HotelDetailsRepository {
        return HotelDetailsRepository(sharedPref, apiInterface, hotelDetailsDao)
    }

    @Singleton
    @Provides
    fun provideHotelRepository(
        sharedPref: SharedPreferences,
        apiInterface: TripAdvisorService,
        hotelsDao: HotelsDao
    ): HotelRepository {
        return HotelRepository(sharedPref, apiInterface, hotelsDao)
    }

    @Singleton
    @Provides
    fun provideLocationSearchRepository(
        sharedPref: SharedPreferences,
        apiInterface: TripAdvisorService,
        locationSearchDao: LocationSearchDao
    ): LocationSearchRepository {
        return LocationSearchRepository(sharedPref, apiInterface, locationSearchDao)
    }

}