package com.example.foodlist.data.local
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodlist.data.local.dao.PostsDao
import com.example.foodlist.model.Post

@Database(
    entities = [Post::class],
    version = 1
)
abstract class FoodiumPostsDatabase : RoomDatabase() {

    abstract fun getPostsDao(): PostsDao

    companion object {
        const val DB_NAME = "foodlist_database"

        @Volatile
        private var INSTANCE: FoodiumPostsDatabase? = null

        fun getInstance(context: Context): FoodiumPostsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        FoodiumPostsDatabase::class.java,
                        DB_NAME
                    )
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}