package com.example.nagyhazi.data.sources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nagyhazi.data.models.Car
import com.example.nagyhazi.data.models.User
import com.google.firebase.database.*
import kotlin.math.ceil

/*
    This class handles all operations related to Firebase Realtime Databae.
 */
class FirebaseDB {
    // names of childs in database
    private val USERS_DB_CHILD: String = "users"
    private val CARS_DB_CHILD: String = "cars"
    // lists of cars
    private val listOfCars = ArrayList<Car>()
    private val listOfCarsMutableLiveData = MutableLiveData<List<Car>>()
    // used for inserting new car into database
    private var carMutableLiveData = MutableLiveData<Car>()
    // reference to root of database
    private val databaseRootRef = FirebaseDatabase.getInstance()
    // lastId denotes from where the query should continues
    private lateinit var lastId: String
    private val numOfTotalCarsMutableLiveData = MutableLiveData<Int>()
    // number of cars per page, used to pagination
    private val CARS_PER_PAGE = 4
    // holds current page number
    private var currentPage = 1
    // number of total pages, initialized with dummy value
    var totalPages = 9
    var totalPageLiveData = MutableLiveData<Int>()
    // holds always models to the given type
    var modelsByTypeArray = ArrayList<String>()
    var modelsByTypeLiveData = MutableLiveData<ArrayList<String>>()

    /*
        * creates an unique key to the user
        * uploads user to database
    */
    fun saveUserToDatabase(user: User) {
        val userUniqueKey = databaseRootRef.reference.child(USERS_DB_CHILD).push().key ?: return
        val email = user.email
        val newUser = User(
            userUniqueKey,
            email,
            isAuthenticated = true,
            isNewUser = true,
            exception = null
        )
        databaseRootRef.reference.child(USERS_DB_CHILD)
            .child(userUniqueKey)
            .setValue(newUser)
    }

    // initializes carMutableLiveData with dummy values
    fun initCarLiveData(): LiveData<Car> {
        val car = Car("", "", "", "", "", false)
        carMutableLiveData.postValue(car)
        return carMutableLiveData
    }

    /*
        * creates an unique key to the car
        * uploads car to the database
        * in case of success changes content of carMutableLiveData
        * isSuccess denotes success
     */
    fun saveCarDetailsToDatabase(type: String, model: String, price: String, imgUrl: String): LiveData<Car>? {
        val carUniqueKey = databaseRootRef.reference.child(CARS_DB_CHILD).push().key ?: return null
        val newCar = Car(carUniqueKey, type, model, price, imgUrl, false)
        databaseRootRef.reference.child(CARS_DB_CHILD)
            .child(carUniqueKey)
            .setValue(newCar)
            .addOnCompleteListener {
                newCar.isSuccess = true
                carMutableLiveData.postValue(newCar)
            }
        return carMutableLiveData
    }

    // returns cars with a specific type
    fun getCarsByType(type: String): LiveData<List<Car>> {
        val carsFilteredByType = ArrayList<Car>()
        databaseRootRef.getReference(CARS_DB_CHILD)
            .orderByChild("type")
            .equalTo(type)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (document in snapshot.children) {
                        val newCar = document.getValue(Car::class.java) as Car
                        carsFilteredByType.add(newCar)
                    }
                    listOfCarsMutableLiveData.value = carsFilteredByType
                }
            })
        return listOfCarsMutableLiveData
    }

    // populates recyclerview with data for first time [first time and other data loading should be differentiated while using pagination
    fun getFirstCars(): LiveData<List<Car>> {
        val query = databaseRootRef.getReference(CARS_DB_CHILD)
            .orderByKey()
            .limitToFirst(CARS_PER_PAGE + 1)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (document in snapshot.children) {
                    val newCar = document.getValue(Car::class.java) as Car
                    listOfCars.add(newCar)
                }
                if (listOfCars.size > CARS_PER_PAGE) {
                    lastId = listOfCars[listOfCars.size - 1].carId
                    listOfCars.removeAt(listOfCars.size - 1)
                }
                else {
                    lastId = listOfCars[listOfCars.size - 1].carId
                }
                listOfCarsMutableLiveData.value = listOfCars
            }
        })
        return listOfCarsMutableLiveData
    }

    fun addMoreCars() {
        currentPage += 1
        val beforeSize = listOfCars.size
        val query = databaseRootRef.getReference(CARS_DB_CHILD)
            .orderByKey()
            .startAt(lastId)
            .limitToFirst(CARS_PER_PAGE + 1)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (document in snapshot.children) {
                    val newCar = document.getValue(Car::class.java) as Car
                    if ((listOfCars.find { it.carId == newCar.carId }) == null) {
                        listOfCars.add(newCar)
                    }
                }
                // originally it was !=
                if(currentPage < totalPages) {
                    lastId = listOfCars[listOfCars.size - 1].carId
                    listOfCars.removeAt(listOfCars.size - 1)
                    listOfCarsMutableLiveData.value = listOfCars
                }
                else {
                    // we reached end of pages, no remove needed
                    listOfCarsMutableLiveData.value = listOfCars
                }
            }
        })
    }

    // calculate total page num for the first time
    fun calcTotalPages() {
        val query = databaseRootRef.getReference(CARS_DB_CHILD).orderByKey()
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val numOfCars = snapshot.childrenCount.toDouble()
                val temp = numOfCars / CARS_PER_PAGE
                val totalPageNum: Int= ceil(temp).toInt()
                totalPages = totalPageNum
            }
        })
    }

    // if new data appears in database, total page num is recalculated
    fun databaseChangeListener():LiveData<Int> {
        databaseRootRef.getReference("cars")
            .addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snap: DataSnapshot) {
                    val numberOfCars = snap.childrenCount.toDouble()
                    val temporary = numberOfCars / CARS_PER_PAGE
                    val numOfPages: Int= ceil(temporary).toInt()
                    if (numOfPages > totalPages) {
                        totalPages = numOfPages
                        totalPageLiveData.value = 1
                    }
                    else {
                        totalPageLiveData.value = 0
                    }
                }
            })
        return totalPageLiveData
    }

    fun getCurrentPage(): Int {
        return currentPage
    }

    fun getTotalPageNum(): Int {
        return totalPages
    }

    // makes modelsByType empty by default
    fun initModelsByType(): LiveData<ArrayList<String>> {
        return modelsByTypeLiveData
    }

    // gets models from database to the corresponding car type
    fun getModelsByType(type: String): LiveData<ArrayList<String>>{
        val type = type.toLowerCase()
        val query = databaseRootRef.getReference("/models/$type")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                modelsByTypeArray.clear()
                for (document in snapshot.children) {
                    modelsByTypeArray.add(document.value.toString())
                }
                modelsByTypeLiveData.value = modelsByTypeArray
            }
        })
        return modelsByTypeLiveData
    }
}