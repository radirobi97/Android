package com.example.foodlist.ui.main.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodlist.R
import com.example.foodlist.model.Post
import com.example.foodlist.ui.main.adapter.PostListAdapter
import kotlinx.android.synthetic.main.item_post.view.*


class PostViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(post: Post, onItemClickListener: PostListAdapter.OnItemClickListener? = null) {
        view.post_title.text = post.title
        view.post_author.text = post.author

        Glide.with(view)
            .load(post.imageUrl)
            .placeholder(R.drawable.ic_photo)
            .error(R.drawable.ic_broken_image)
            .into(view.imageView)

        onItemClickListener?.let { listener ->
            view.item_holder.setOnClickListener {
                listener.onItemClicked(post, view.imageView)
            }
        }
    }
}
