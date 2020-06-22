package com.example.nagyhazi.views.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.nagyhazi.data.sources.FirebaseDB

/*
    This ViewModel class is related to searchFragment.
 */
class SearchViewModel: ViewModel() {

    private val firebaseDB = FirebaseDB()
    lateinit var modelsByType: LiveData<ArrayList<String>>

    // initializes LiveData objects with dummy values
    fun initSpinnerLiveData() {
        modelsByType = firebaseDB.initModelsByType()
    }

    /*
    * gets list of models to the given type
 */
    fun getModelsByType(type: String) {
        firebaseDB.getModelsByType(type)
    }
}