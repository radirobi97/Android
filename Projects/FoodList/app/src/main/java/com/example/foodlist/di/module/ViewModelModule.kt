package com.example.foodlist.di.module

import androidx.lifecycle.ViewModel
import com.example.foodlist.di.annotation.ViewModelKey
import com.example.foodlist.ui.details.PostDetailsViewModel
import com.example.foodlist.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMyViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailsViewModel::class)
    abstract fun bindPostDetailsViewModel(viewModel: PostDetailsViewModel): ViewModel
}