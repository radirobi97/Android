
package com.example.foodlist.data.remote

import com.example.foodlist.model.Post
import retrofit2.Response
import retrofit2.http.GET


interface FoodListService {

    @GET("/DummyFoodiumApi/api/posts/")
    suspend fun getPosts(): Response<List<Post>>

    companion object {
        const val FOODIUM_API_URL = "https://patilshreyas.github.io"
    }
}