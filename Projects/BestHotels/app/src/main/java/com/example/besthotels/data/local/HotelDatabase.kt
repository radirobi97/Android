package com.example.besthotels.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.besthotels.data.local.daos.HotelDetailsDao
import com.example.besthotels.data.local.daos.HotelsDao
import com.example.besthotels.data.local.daos.LocationSearchDao
import com.example.besthotels.data.model.*

@Database(
    entities = [
        LocationSearch::class,
        Hotel::class,
        Photo::class,
        Images::class,
        Original::class,
        HotelDetails::class,
        Amenity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class HotelDatabase: RoomDatabase() {

    abstract fun getLocationSearchDao(): LocationSearchDao
    abstract fun getHotelsDao(): HotelsDao
    abstract fun getHotelDetailsDao(): HotelDetailsDao

    companion object {
        const val DB_NAME = "hotels_database"

        @Volatile
        private var INSTANCE: HotelDatabase? = null

        fun getInstance(context: Context): HotelDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HotelDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}