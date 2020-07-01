package com.example.moovieapp.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("top250_min.json")
    suspend fun getTop250Movies(): Response<List<Movie>>
}