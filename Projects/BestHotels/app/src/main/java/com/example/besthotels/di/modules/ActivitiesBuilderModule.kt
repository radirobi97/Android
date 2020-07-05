package com.example.besthotels.di.modules


import com.example.besthotels.MainActivity
import com.example.besthotels.ui.fragments.detail.HotelDetailsFragment
import com.example.besthotels.ui.fragments.hotels.HotelFragment
import com.example.besthotels.ui.fragments.location.LocationSearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivitiesBuilderModule {

    @ContributesAndroidInjector
    abstract fun getMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun getHotelDetailsFragment(): HotelDetailsFragment

    @ContributesAndroidInjector
    abstract fun getLocationSearchFragment(): LocationSearchFragment

    @ContributesAndroidInjector
    abstract fun getHotelFragment(): HotelFragment

}