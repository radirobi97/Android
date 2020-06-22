package com.example.foodlist.di.component

import android.app.Application
import com.example.foodlist.FoodApp
import com.example.foodlist.di.builder.ActivityBuilder
import com.example.foodlist.di.module.FoodApiModule
import com.example.foodlist.di.module.FoodiumDatabaseModule
import com.example.foodlist.di.module.ViewModelFactoryModule
import com.example.foodlist.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        FoodiumDatabaseModule::class,
        FoodApiModule::class,
        ActivityBuilder::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<FoodApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: FoodApp)
}