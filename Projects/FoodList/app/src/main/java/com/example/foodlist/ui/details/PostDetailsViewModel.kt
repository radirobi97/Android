package com.example.foodlist.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foodlist.data.repository.PostsRepository
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(private val postsRepository: PostsRepository) : ViewModel() {

    fun getPost(id: Int) = postsRepository.getPostById(id).asLiveData()
}
