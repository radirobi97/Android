package com.example.besthotels.di.modules

import com.example.besthotels.data.remote.TripAdvisorService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideInterceptor() = Interceptor{ chain ->
        val request = chain.request()
            .newBuilder()
            .header("x-rapidapi-host","tripadvisor1.p.rapidapi.com")
            .header("x-rapidapi-key", "8b3c69ed06msh8876d4e5a0eb7bap10ab03jsnbaa6d75c8c2e")
            .build()
        chain.proceed(request)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor) =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(interceptor)
            .build()


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://tripadvisor1.p.rapidapi.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): TripAdvisorService {
        return retrofit.create(TripAdvisorService::class.java)
    }
}