package com.example.moovieapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moovieapp.R
import com.example.moovieapp.data.remote.Movie
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val callback: (movie: Movie, mcvPoster: MaterialCardView, tvTitle: TextView) -> Unit) : ListAdapter<Movie, MovieAdapter.ViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.cl_movie.setOnClickListener {
                callback(getItem(layoutPosition), view.mcv_poster, view.tv_title)
            }
        }

        fun bind(movie: Movie) {
            view.tv_title.text = movie.name
            Glide.with(view)
                .load(movie.thumbUrl)
                .into(view.iv_poster)
        }
    }

}

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}