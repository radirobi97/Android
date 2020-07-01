package com.example.moovieapp.ui.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moovieapp.ui.activities.feed.FeedActivity
import com.example.moovieapp.R
import com.example.moovieapp.utils.State
import dagger.android.AndroidInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashViewModel = ViewModelProvider(this, factory).get(SplashViewModel::class.java)
        splashViewModel.launcherActivity.observe(this, Observer { _ ->
            val startFeedActivityIntent = Intent(this, FeedActivity::class.java)
            startActivity(startFeedActivityIntent)
            finish()
        })
    }

}