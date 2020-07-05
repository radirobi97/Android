package com.example.besthotels.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.besthotels.R
import com.example.besthotels.model.AmenityFilter
import kotlinx.android.synthetic.main.layout_amenities.view.*


class AmenityRecyclerAdapter(private val amenityList: MutableList<AmenityFilter>) :
    RecyclerView.Adapter<AmenityRecyclerAdapter.AmenityListViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AmenityListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.layout_amenities, parent, false)
    )

    override fun onBindViewHolder(holder: AmenityListViewHolder, position: Int) =
        holder.bind(amenityList[position])

    override fun getItemCount(): Int {
        return amenityList.size
    }


    inner class AmenityListViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun bind(amenity: AmenityFilter) {
            Glide.with(view)
                .load(amenity.icon)
                .placeholder(R.drawable.ic_image_placeholder)
                .apply( RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(view.amenityIv)
            view.amenityTv.text = amenity.name
        }
    }
}



