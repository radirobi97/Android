package com.example.moovieapp.ui.activities.feed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moovieapp.R
import com.example.moovieapp.data.remote.Movie
import com.example.moovieapp.ui.activities.movie.MovieActivity
import com.example.moovieapp.ui.adapters.FeedAdapter
import com.example.moovieapp.utils.State
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class FeedActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var feedViewModel: FeedViewModel

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        initImageButtonsResource()
        setOnClickListeners()

        val adapter = FeedAdapter { movie, poster, title -> goToMovieActivity(movie, poster, title)}
        rv_feed.adapter = adapter


        feedViewModel = ViewModelProvider(this, factory).get(FeedViewModel::class.java)

        feedViewModel.darkMode.observe(this, Observer { isDarkMode ->

            val darkModeFlag = if (isDarkMode) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(darkModeFlag)
        })

        feedViewModel.toast.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        feedViewModel.movies.observe(this, Observer {

            when (it) {

                is State.Loading -> {
                    pb_feed.visibility = View.VISIBLE
                    rv_feed.visibility = View.GONE
                }

                is State.Success -> {
                    pb_feed.visibility = View.GONE
                    rv_feed.visibility  = View.VISIBLE
                    adapter.submitList(it.data)
                }

                is State.Error -> {
                    rv_feed.visibility = View.GONE
                }
            }
        })

    }

    private fun initImageButtonsResource() {
        ib_sort_order.tag = R.drawable.ic_star
        ib_sort_order.setImageResource(R.drawable.ic_star)
    }

    private fun setOnClickListeners() {
        ib_sort_order.setOnClickListener {
            val newIconResource = feedViewModel.onToggleSortOrderClicked(ib_sort_order.tag as Int)
            ib_sort_order.tag = newIconResource
            ib_sort_order.setImageResource(newIconResource)
        }
        ib_dark_mode.setOnClickListener {
            feedViewModel.onToggleDarkModeClicked()
        }
    }

    private fun goToMovieActivity(movie: Movie, mcvPoster: View, tvTitle: View) {
        val transition = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            Pair(tvTitle, "title"),
            Pair(mcvPoster, "poster")
        )
        val startMovieActivityIntent = Intent(this, MovieActivity::class.java).apply {
            putExtra(MovieActivity.KEY_MOVIE, movie)
        }
        startActivity(startMovieActivityIntent, transition.toBundle())
    }
}