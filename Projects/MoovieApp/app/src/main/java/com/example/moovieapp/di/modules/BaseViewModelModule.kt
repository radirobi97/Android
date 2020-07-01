package com.example.moovieapp.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.moovieapp.utils.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class BaseViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}