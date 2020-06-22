package com.example.foodlist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Post.TABLE_NAME)
data class Post(

    @PrimaryKey
    var id: Int? = 0,
    var title: String? = null,
    var author: String? = null,
    var body: String? = null,
    var imageUrl: String? = null
) {
    companion object {
        const val TABLE_NAME = "foodlist_posts"
    }
}