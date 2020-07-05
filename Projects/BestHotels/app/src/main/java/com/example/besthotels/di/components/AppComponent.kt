package com.example.besthotels.di.components


import android.app.Application
import com.example.besthotels.App
import com.example.besthotels.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivitiesBuilderModule::class,
        DatabaseModule::class,
        BaseViewModelModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        PreferenceModule::class,
        RepoModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(application: Application): Builder

        fun build(): AppComponent
    }

}