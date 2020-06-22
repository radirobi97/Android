package com.example.foodlist.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.foodlist.ViewModelProviderFactory
import dagger.Binds
import dagger.Module


@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}