package com.example.foodlist.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodlist.R
import com.example.foodlist.model.Post
import com.example.foodlist.ui.main.MainViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_post_details.*
import kotlinx.android.synthetic.main.content_post_details.view.*
import kotlinx.android.synthetic.main.content_post_details.view.post_author
import kotlinx.android.synthetic.main.content_post_details.view.post_title
import kotlinx.android.synthetic.main.item_post.view.*
import javax.inject.Inject

class PostDetailsActivity : AppCompatActivity() {

    private lateinit var post: Post
    @Inject
    protected lateinit var mViewModelProvider: ViewModelProvider.Factory

    protected val mViewModel by lazy {
        ViewModelProvider(this, mViewModelProvider).get(PostDetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val postId = intent.extras?.getInt(POST_ID)
            ?: throw IllegalArgumentException("postId must be non-null")

        initPost(postId)
    }

    private fun initPost(postId: Int) {
        mViewModel.getPost(postId).observe(this, Observer { post ->
            postContent.apply {
                this@PostDetailsActivity.post = post
                post_title.text = post.title
                post_author.text = post.author
                post_body.text = post.body
            }
            Glide.with(this)
                .load(post.imageUrl)
                .into(imageView)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }


    companion object {
        const val POST_ID = "postId"
    }
}