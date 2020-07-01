package com.example.moovieapp.ui.activities.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SplashViewModel @Inject constructor(): ViewModel() {

    companion object {
        const val SPLASH_TIME = 3_000L
        const val START_SPLASING = "start_splashing"
    }

    @ExperimentalCoroutinesApi
    val launcherActivity = flowOf(START_SPLASING)
        .onStart {
            delay(SPLASH_TIME)
        }
        .asLiveData()
}