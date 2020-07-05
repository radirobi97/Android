package com.example.besthotels.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.besthotels.R
import com.example.besthotels.model.Country
import kotlinx.android.synthetic.main.layout_grid_images.view.*


class StaggeredRecyclerAdapter(private val onItemClickListener: OnItemClickListener):
        ListAdapter<Country, StaggeredRecyclerAdapter.StaggeredViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaggeredViewHolder {
        return StaggeredViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_grid_images, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StaggeredViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClickListener)


    inner class StaggeredViewHolder(private val view : View) : RecyclerView.ViewHolder(view){

        fun bind(country: Country, onItemClickListener: OnItemClickListener) {
            Glide.with(view)
                .load(country.image_url)
                .placeholder(R.drawable.ic_image_placeholder)
                .apply( RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(view.iv_country)
            view.tv_country.text = country.name
            view.cv_country.setOnClickListener {
                onItemClickListener.onCountryClick(view.tv_country.text.toString())
            }
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Country>() {
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean =
                oldItem == newItem
        }
    }


    interface OnItemClickListener {
        fun onCountryClick(countryName: String)
    }
}