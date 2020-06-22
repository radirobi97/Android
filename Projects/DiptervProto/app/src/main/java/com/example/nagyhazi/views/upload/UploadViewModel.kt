package com.example.nagyhazi.views.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.nagyhazi.data.models.Car
import com.example.nagyhazi.data.sources.FirebaseDB
import com.example.nagyhazi.data.sources.FirebaseStorage

/*
    This ViewModel class is related to UploadActivity.
 */
class UploadViewModel: ViewModel() {

    lateinit var uploadedCarLiveData: LiveData<Car>
    lateinit var downloadLinkLiveData: LiveData<String>
    lateinit var modelsByType: LiveData<ArrayList<String>>
    private val firebaseStorage = FirebaseStorage()
    private val firebaseDB = FirebaseDB()

    // initializes LiveData objects with dummy values
    fun initCarAndDownloadLinkAndSpinnerLiveData() {
        uploadedCarLiveData = firebaseDB.initCarLiveData()
        downloadLinkLiveData = firebaseStorage.initDownloadLink()
        modelsByType = firebaseDB.initModelsByType()
    }

    /*
        * uploads image to FireStore
        * changes content of downloadLinkLiveData so proper observer can respond to it
    */
    fun uploadImageToFireStore(imageInByteArray: ByteArray) {
        downloadLinkLiveData = firebaseStorage.uploadImageToStorage(imageInByteArray)
    }

    /*
        * uploads details of the car to database
        * changes content of uploadedCarLiveData so proper observer can respond to it
    */
    fun uploadCarDetailsToDatabase(type: String, model: String, price: String, imgUrl: String) {
       uploadedCarLiveData = firebaseDB.saveCarDetailsToDatabase(type, model, price, imgUrl) as LiveData<Car>
    }


    /*
        * gets list of models to the given type
     */
    fun getModelsByType(type: String) {
        firebaseDB.getModelsByType(type)
    }
}