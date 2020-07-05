package com.example.besthotels.data.local.daos


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.besthotels.data.model.LocationSearch
import kotlinx.coroutines.flow.Flow


@Dao
interface LocationSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(locationSearch: LocationSearch)

    @Query("SELECT * FROM location_search WHERE name=:name")
    fun getLocationByName(name: String) : Flow<LocationSearch>

}