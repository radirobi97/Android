package com.example.nagyhazi.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nagyhazi.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Car::class), version = 1, exportSchema = false)
public abstract class CarRoomDatabase: RoomDatabase(){

    abstract fun carDao(): CarDao

    private class CarDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.carDao())
                }
            }
        }



        suspend fun populateDatabase(carDao: CarDao) {

            carDao.deleteAllCars()
            var car = Car("Mazda", "hatoska", 1000000, R.drawable.car)
            carDao.insertCar(car)

            var car2 = Car("Ford", "heteske", 1000000, R.drawable.car)
            carDao.insertCar(car2)

            var car3 = Car("Has", "otoske", 1000000, R.drawable.car)
            carDao.insertCar(car3)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: CarRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): CarRoomDatabase {
            Log.d("HOME", "car database created")
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarRoomDatabase::class.java,
                    "car_database"
                ).addCallback(CarDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}