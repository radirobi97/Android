package com.example.foodlist.di.builder

import com.example.foodlist.ui.details.PostDetailsActivity
import com.example.foodlist.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindPostDetailsActivity(): PostDetailsActivity
}