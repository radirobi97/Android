package com.example.besthotels.di.modules

import androidx.lifecycle.ViewModel
import com.example.besthotels.di.annotation.ViewModelKey
import com.example.besthotels.ui.fragments.detail.HotelDetailsViewModel
import com.example.besthotels.ui.fragments.hotels.HotelsViewModel
import com.example.besthotels.ui.fragments.location.LocationSearchViewModel
import com.example.besthotels.ui.shared.SharedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HotelDetailsViewModel::class)
    abstract fun bindHotelDetailsViewModel(viewModel: HotelDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HotelsViewModel::class)
    abstract fun bindHotelsViewModel(viewModel: HotelsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationSearchViewModel::class)
    abstract fun bindLocationSearchViewModel(viewModel: LocationSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SharedViewModel::class)
    abstract fun bindSharedViewModel(viewModel: SharedViewModel): ViewModel
}