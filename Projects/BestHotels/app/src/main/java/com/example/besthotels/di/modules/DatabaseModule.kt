package com.example.besthotels.di.modules

import android.app.Application
import com.example.besthotels.data.local.HotelDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = HotelDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideHotelDetailsDao(database: HotelDatabase) = database.getHotelDetailsDao()

    @Singleton
    @Provides
    fun provideHotelsDao(database: HotelDatabase) = database.getHotelsDao()

    @Singleton
    @Provides
    fun provideLocationSearchDao(database: HotelDatabase) = database.getLocationSearchDao()
}