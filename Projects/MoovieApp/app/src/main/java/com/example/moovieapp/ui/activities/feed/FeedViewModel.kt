package com.example.moovieapp.ui.activities.feed

import android.util.Log
import androidx.lifecycle.*
import com.example.moovieapp.R
import com.example.moovieapp.data.remote.Movie
import com.example.moovieapp.data.repositories.MoviesRepo
import com.example.moovieapp.models.FeedItem
import com.example.moovieapp.utils.SingleLiveEvent
import com.example.moovieapp.utils.State
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FeedViewModel @Inject constructor(moviesRepo: MoviesRepo): ViewModel() {

    private val sortedOrder = MutableLiveData<Int>()
    private val _toast = MutableLiveData<Int>()
    val toast: LiveData<Int> = _toast
    private val _darkMode = SingleLiveEvent<Boolean>()
    val darkMode: LiveData<Boolean> = _darkMode

    init {
        sortedOrder.value = SORT_ORDER_YEAR
    }


    val movies: LiveData<State<List<FeedItem>>> = sortedOrder.switchMap { sortOrder ->
        moviesRepo
            .getTop250Movies()
            .map {
                when(it) {
                    is State.Success -> {
                        State.success(convertToFeed(it.data, sortOrder))
                    }
                    is State.Error<*> -> {
                        State.error<List<FeedItem>>(it.message)
                    }
                    is State.Loading<*> -> {
                        State.loading()
                    }
                }
            }
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun onToggleDarkModeClicked() {
        val newDarkMode = !(darkMode.value ?: false)
        _darkMode.value = newDarkMode
    }

    fun onToggleSortOrderClicked(src: Int): Int {
        val newSortOrderIcon = if(src == R.drawable.ic_calendar) {
            R.drawable.ic_star
        } else {
            R.drawable.ic_calendar
        }

        when(newSortOrderIcon) {
            R.drawable.ic_star -> {
                sortedOrder.value = SORT_ORDER_YEAR
                _toast.value = R.string.feed_toast_sort_year
            }

            R.drawable.ic_calendar -> {
                sortedOrder.value = SORT_ORDER_RATING
                _toast.value = R.string.feed_toast_sort_rating
            }

            else -> throw IllegalArgumentException("TSH : $newSortOrderIcon not managed")
        }

        return newSortOrderIcon
    }

    companion object {

        const val SORT_ORDER_YEAR = 1
        private const val SORT_ORDER_RATING = 2

        fun convertToFeed(movies: List<Movie>, sortOrder: Int): List<FeedItem> {
            val genreSet = mutableSetOf<String>()

            for (movie in movies) {
                for (genre in movie.genre) {
                    genreSet.add(genre)
                }
            }

            val feedItems = mutableListOf<FeedItem>()

            for ((index,genre) in genreSet.withIndex()) {
                val genreMovies = movies
                    .filter { it.genre.contains(genre) }
                    .sortedByDescending {
                        when (sortOrder) {
                            SORT_ORDER_RATING -> it.rating
                            SORT_ORDER_YEAR -> it.year.toFloat()
                            else -> {
                                throw IllegalArgumentException("TSH : sort order '$sortOrder' not managed")
                            }
                        }
                    }

                feedItems.add(
                    FeedItem(
                        index.toLong(),
                        genre,
                        genreMovies
                    )
                )
            }
            return feedItems
        }
    }
}