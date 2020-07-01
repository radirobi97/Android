package com.example.moovieapp.data.remote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "movies"
)
data class Movie(
    val actors: List<String>,
    val desc: String,
    val directors: List<String>,
    val genre: List<String>,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("thumb_url")
    val thumbUrl: String,
    @SerializedName("imdb_url")
    val imdbUrl: String,
    val name: String,
    val rating: Float,
    val year: Int
): Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}