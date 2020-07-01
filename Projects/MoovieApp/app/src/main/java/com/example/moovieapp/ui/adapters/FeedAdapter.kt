package com.example.moovieapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moovieapp.R
import com.example.moovieapp.data.remote.Movie
import com.example.moovieapp.models.FeedItem
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item_feed.view.*

class FeedAdapter(private val onMovieClicked: (movie: Movie, mcvPoster: MaterialCardView, tvTitle: TextView) -> Unit) : ListAdapter<FeedItem, FeedAdapter.ViewHolder>(FeedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val moviesAdapter by lazy {
            val adapter = MovieAdapter(onMovieClicked)
            view.rv_movies.adapter = adapter
            adapter
        }

        fun bind(feed: FeedItem) {
            view.tv_genre.text = feed.genre
            moviesAdapter.submitList(feed.movies)
        }
    }
}

class FeedDiffCallback : DiffUtil.ItemCallback<FeedItem>() {

    override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        return oldItem == newItem
    }
}