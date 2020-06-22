package com.example.foodlist.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodlist.data.repository.PostsRepository
import com.example.foodlist.model.Post
import com.example.foodlist.util.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val postsRepository: PostsRepository) : ViewModel() {

    private val _postsLiveData = MutableLiveData<State<List<Post>>>()

    val postsLiveData: LiveData<State<List<Post>>>
        get() = _postsLiveData

    fun getPosts() {
        viewModelScope.launch {
            postsRepository.getAllPosts().collect {
                _postsLiveData.value = it
            }
        }
    }
}
