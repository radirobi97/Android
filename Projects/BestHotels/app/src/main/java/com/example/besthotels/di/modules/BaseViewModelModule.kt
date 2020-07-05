package com.example.besthotels.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.besthotels.util.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class BaseViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}