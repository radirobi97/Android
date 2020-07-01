package com.example.moovieapp.ui.activities.movie

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moovieapp.R
import com.example.moovieapp.data.remote.Movie
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.item_movie.view.*
import javax.inject.Inject

class MovieActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var movieViewModel: MovieViewModel

    companion object {
        const val KEY_MOVIE = "movie"
        const val IMDB_URL= "https://imdb.com"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        movieViewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)
        val movie = intent.getSerializableExtra(KEY_MOVIE) as Movie
        movieViewModel.init(movie)
        mapMovieDetailsToViews()
        setOnClickListener()

        movieViewModel.closeActivity.observe(this, Observer {
            finish()
        })

        movieViewModel.openImdb.observe(this, Observer {
            val openImdbIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse("$IMDB_URL${movieViewModel.movie!!.imdbUrl}")
            )
            startActivity(openImdbIntent)
        })
    }

    private fun mapMovieDetailsToViews() {
        tv_rating.text = movieViewModel.movie!!.rating.toString()
        tv_director.text = movieViewModel.movie!!.directors.toString()
        tv_actor.text = movieViewModel.movie!!.actors.toString()
        tv_genre.text = movieViewModel.movie!!.genre.toString()
        tv_title.text = movieViewModel.movie!!.name
        tv_description.text = movieViewModel.movie!!.desc
        Glide.with(iv_mcv_image)
            .load(movieViewModel.movie!!.thumbUrl)
            .into(iv_mcv_image)
    }

    private fun setOnClickListener() {
        ib_go_back.setOnClickListener {
            movieViewModel.onBackButtonClicked()
        }
        btn_movie_open_imdb.setOnClickListener {
            movieViewModel.onGoToImdbClicked()
        }
    }
}