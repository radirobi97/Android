package com.example.moovieapp.di.modules

import android.content.SharedPreferences
import com.example.moovieapp.data.local.daos.MoviesDao
import com.example.moovieapp.data.remote.ApiInterface
import com.example.moovieapp.data.repositories.MoviesRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideMoviesRepo(
        sharedPref: SharedPreferences,
        apiInterface: ApiInterface,
        moviesDao: MoviesDao
    ): MoviesRepo {
        return MoviesRepo(sharedPref, apiInterface, moviesDao)
    }

}