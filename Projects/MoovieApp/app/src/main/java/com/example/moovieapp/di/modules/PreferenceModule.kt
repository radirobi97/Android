package com.example.moovieapp.di.modules

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferenceModule {

    @Singleton
    @Provides
    fun providePreference(app: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app.applicationContext)
    }
}