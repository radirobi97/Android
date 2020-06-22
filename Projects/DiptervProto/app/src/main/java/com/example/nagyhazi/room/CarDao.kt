package com.example.diptervproto.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CarDao {

    @Query("SELECT * FROM car_table")
    fun getCars(): LiveData<List<Car>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCar(car: Car)

    @Query("SELECT count(*) FROM car_table")
    suspend fun getDaoSize(): Int

    @Query("DELETE FROM car_table")
    suspend fun deleteAllCars()
}