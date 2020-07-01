package com.example.moovieapp.di.modules

import com.example.moovieapp.ui.activities.feed.FeedActivity
import com.example.moovieapp.ui.activities.movie.MovieActivity
import com.example.moovieapp.ui.activities.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuilderModule {

    @ContributesAndroidInjector
    abstract fun getSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun getMainActivity(): FeedActivity

    @ContributesAndroidInjector
    abstract fun getMovieActivity(): MovieActivity

}