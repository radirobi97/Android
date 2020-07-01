package com.example.moovieapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.moovieapp.ui.activities.feed.FeedViewModel
import com.example.moovieapp.ui.activities.movie.MovieViewModel
import com.example.moovieapp.ui.activities.splash.SplashViewModel
import com.example.moovieapp.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun bindMainViewModel(viewModel: FeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(viewModel: MovieViewModel): ViewModel

}