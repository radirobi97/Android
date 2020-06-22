package com.example.foodlist.di.module

import android.app.Application
import com.example.foodlist.data.local.FoodiumPostsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FoodiumDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = FoodiumPostsDatabase.getInstance(application)

    @Singleton
    @Provides
    fun providePostsDao(database: FoodiumPostsDatabase) = database.getPostsDao()
}