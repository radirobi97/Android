package com.example.foodlist.ui.main

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodlist.R
import com.example.foodlist.model.Post
import com.example.foodlist.ui.details.PostDetailsActivity
import com.example.foodlist.ui.main.adapter.PostListAdapter
import com.example.foodlist.util.State
import com.shreyaspatil.MaterialDialog.MaterialDialog
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), PostListAdapter.OnItemClickListener {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    val viewModel by lazy {
        ViewModelProvider(this, viewModelProvider).get(MainViewModel::class.java)
    }

    private val mAdapter: PostListAdapter by lazy {
        PostListAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }

        initPosts()
    }

    private fun initPosts() {
        viewModel.postsLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> {
                    showLoading(true)
                }
                is State.Success -> {
                    if (state.data.isNotEmpty()) {
                        mAdapter.submitList(state.data.toMutableList())
                        showLoading(false)
                    }
                }
                is State.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT)
                    showLoading(false)
                }
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            getPosts()
        }

        if (viewModel.postsLiveData.value !is State.Success) {
            getPosts()
        }
    }

    private fun getPosts() {
        viewModel.getPosts()
    }

    private fun showLoading(isLoading: Boolean) {
        swipeRefreshLayout.isRefreshing = isLoading
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_theme -> {
                val mode =
                    if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                        Configuration.UI_MODE_NIGHT_NO
                    ) {
                        AppCompatDelegate.MODE_NIGHT_YES
                    } else {
                        AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                    }
                AppCompatDelegate.setDefaultNightMode(mode)
                true
            }
            else -> true
        }
    }

    override fun onItemClicked(post: Post, imageView: ImageView) {
        val intent = Intent(this, PostDetailsActivity::class.java)
        intent.putExtra(PostDetailsActivity.POST_ID, post.id)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            imageView,
            imageView.transitionName
        )

        startActivity(intent, options.toBundle())
    }
}