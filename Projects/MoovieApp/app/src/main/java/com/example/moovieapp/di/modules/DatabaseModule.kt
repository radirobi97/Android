package com.example.moovieapp.di.modules

import android.app.Application
import com.example.moovieapp.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = AppDatabase.getInstance(application)

    @Singleton
    @Provides
    fun providePostsDao(database: AppDatabase) = database.getMoviesDao()
}