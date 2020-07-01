package com.example.moovieapp.models

import com.example.moovieapp.data.remote.Movie

data class FeedItem(
    val id: Long,
    val genre: String,
    val movies: List<Movie>
)