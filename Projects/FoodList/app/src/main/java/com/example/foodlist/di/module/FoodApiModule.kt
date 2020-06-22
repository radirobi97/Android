package com.example.foodlist.di.module

import com.example.foodlist.data.remote.FoodListService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class FoodApiModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): FoodListService = Retrofit.Builder()
        .baseUrl(FoodListService.FOODIUM_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FoodListService::class.java)
}