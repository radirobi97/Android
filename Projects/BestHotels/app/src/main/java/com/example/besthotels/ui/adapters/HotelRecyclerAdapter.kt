package com.example.besthotels.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.besthotels.R
import com.example.besthotels.data.model.Hotel
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.layout_hotel_items.view.*


class HotelRecyclerAdapter(private val onItemClickListener: OnItemClickListener):
    ListAdapter<Hotel, HotelRecyclerAdapter.HotelListViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelListViewHolder {
        return HotelListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_hotel_items, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HotelListViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClickListener)


    inner class HotelListViewHolder(private val view : View) : RecyclerView.ViewHolder(view){

        fun bind(hotel: Hotel, onItemClickListener: OnItemClickListener) {

            view.hotelName.text = hotel.name
            view.hotelName.transitionName = hotel.name
            view.hotelNumReviews.text = "(" + hotel.numReviews.toString() + " reviews)"
            view.hotelRatings.text = hotel.rating.toString()
            view.hotelPrice.text = hotel.price.toString()
            view.hotelImageView.apply{
                transitionName = hotel.photo!!.images.original.url
                Glide.with(view)
                    .load(hotel.photo!!.images.original.url)
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(this)
            }

            view.hotelCardView.setOnClickListener {
                onItemClickListener.onHotelClick(hotel, view.hotelImageView, view.hotelName)
            }
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Hotel>() {
            override fun areItemsTheSame(oldItem: Hotel, newItem: Hotel): Boolean =
                oldItem.locationId == newItem.locationId

            override fun areContentsTheSame(oldItem: Hotel, newItem: Hotel): Boolean =
                oldItem == newItem
        }
    }


    interface OnItemClickListener {
        fun onHotelClick(hotel: Hotel, iv: AppCompatImageView, tv: MaterialTextView)
    }
}