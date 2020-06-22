package com.example.nagyhazi.utils

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(val layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition()
        Log.d("finish_debug", "SCROLL HAPPENED")

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPos) >= totalItemCount && firstVisibleItemPos >= 0) {
                Log.d("finish_debug", "SCROLL FELTETEL TELJESULT")
                loadMoreItems()
            }
            else {
                Log.d("finish_debug", "SCROLL FELTETEL NEM TELJESULT")
            }
        }
    }

    abstract fun isLoading(): Boolean

    abstract fun isLastPage(): Boolean

    abstract fun loadMoreItems()
}