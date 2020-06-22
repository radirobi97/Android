package com.example.foodlist.data.repository

import com.example.foodlist.data.local.dao.PostsDao
import com.example.foodlist.data.remote.FoodListService
import com.example.foodlist.model.Post
import com.example.foodlist.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepository @Inject constructor(
    private val postsDao: PostsDao,
    private val foodiumService: FoodListService
) {

    fun getAllPosts(): Flow<State<List<Post>>> {
        return object : NetworkBoundRepository<List<Post>, List<Post>>() {

            override suspend fun saveRemoteData(response: List<Post>) =
                postsDao.insertPosts(response)

            override fun fetchFromLocal(): Flow<List<Post>> = postsDao.getAllPosts()

            override suspend fun fetchFromRemote(): Response<List<Post>> = foodiumService.getPosts()

        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getPostById(postId: Int): Flow<Post> = postsDao.getPostById(postId)
}
