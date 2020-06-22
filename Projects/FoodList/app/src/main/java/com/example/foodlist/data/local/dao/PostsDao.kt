package com.example.foodlist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodlist.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(posts: List<Post>)

    @Query("DELETE FROM ${Post.TABLE_NAME}")
    fun deleteAllPosts()

    @Query("SELECT * FROM ${Post.TABLE_NAME} WHERE ID = :postId")
    fun getPostById(postId: Int): Flow<Post>

    @Query("SELECT * FROM ${Post.TABLE_NAME}")
    fun getAllPosts(): Flow<List<Post>>
}