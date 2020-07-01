package com.example.moovieapp.ui.activities.movie

import androidx.lifecycle.ViewModel
import com.example.moovieapp.data.remote.Movie
import com.example.moovieapp.utils.SingleLiveEvent
import javax.inject.Inject

class MovieViewModel @Inject constructor(): ViewModel() {

    val openImdb = SingleLiveEvent<Boolean>()
    val closeActivity = SingleLiveEvent<Boolean>()
    var movie: Movie? = null

    fun init(movie: Movie) {
        this.movie = movie
    }

    fun onBackButtonClicked() {
        closeActivity.value = true
    }

    fun onGoToImdbClicked() {
        openImdb.value = true
    }
}