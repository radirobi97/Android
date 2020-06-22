package com.example.diptervproto.views.carlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diptervproto.R
import com.example.diptervproto.data.models.Car
import com.example.diptervproto.data.sources.FirebaseDB
import com.example.diptervproto.utils.PaginationScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

/*
    This fragment contains cars in form of recyclerview. Pagination is used to load more and more data.
 */
class CarListFragment(): Fragment(){

    // holds reference to context of MainActivity
    private lateinit var activityContext: Context
    private lateinit var mCarListRecyclerViewAdapter: CarListRecyclerViewAdapter
    private lateinit var carViewModel: CarViewModel
    // indicates if there were any configuration changes
    private var isRecreated = 0
    // variables used during pagination
    private var isLoading = false
    private var isLastPage = false
    private val PAGE_START = 1

    companion object {
        // TAG which holds the class name
        const val TAG = "CarListFragment"
        // method to instantiate fragment
        fun newInstance(): CarListFragment {
            return CarListFragment()
        }
    }

    // initializes carViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        carViewModel = ViewModelProvider(activity!!).get(CarViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    // gets data out from bundle and creates the recyclerview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(savedInstanceState != null) {
            isRecreated = savedInstanceState?.getInt("recreated")
        }
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    /*
        * defines layout manager to recyclerview
        * attaches adapter to recyclerview
        * defines observer to live data
        * adds divider between recyclerview items
        * defines onScrollListener to recyclerview
    */
    private fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activityContext)
        recyclerCarList.layoutManager = linearLayoutManager
        mCarListRecyclerViewAdapter = CarListRecyclerViewAdapter(activity!!.applicationContext)
        recyclerCarList.adapter = mCarListRecyclerViewAdapter

        val carObserver = Observer<List<Car>> { cars ->
            updateCarRecyclerViewContent(cars)
        }
        val pageObserver = Observer<Int> {
            if( it == 1) {
                isLastPage = false
            }
        }
        carViewModel.allCars.observe(viewLifecycleOwner, carObserver)
        carViewModel.totalPageSize.observe(viewLifecycleOwner, pageObserver)
        val dividerItemDecoration = DividerItemDecoration(recyclerCarList.context, LinearLayoutManager.VERTICAL)
        recyclerCarList.addItemDecoration(dividerItemDecoration)

        recyclerCarList.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager){
            override fun isLoading(): Boolean = isLoading

            override fun isLastPage(): Boolean = isLastPage

            override fun loadMoreItems() {
                // indicates that loading in progress
                isLoading = true
                // loads more items
                loadNextPage()
            }
        })
    }

    // first time data loading into recyclerview should be differentiated from other loadings
    private fun updateCarRecyclerViewContent(cars: List<Car>) {
        // first time data loading
        if (carViewModel.getCurrentPage() == PAGE_START) {
            // loads data to recyclerview
            mCarListRecyclerViewAdapter.setCars(cars)
            // loads progress bar item to recycler view which will be removed during loading more items
            addLoadingItemToRecyclerView()
        }
        else {
            // after configuration changes there is no progress so we have to add it to be able to remove later
            if (isRecreated == 1) {
                addLoadingItemToRecyclerView()
                // this means we have handled custom settings after configuration changes
                isRecreated = 0
            }
            // removing progress bar from recyclerview
            mCarListRecyclerViewAdapter.removeLoadingItem()
            // loading is done
            isLoading = false
            mCarListRecyclerViewAdapter.setCars(cars)
            addLoadingItemToRecyclerView()
        }
    }

    // adds loading progress bar into recyclerview
    private fun addLoadingItemToRecyclerView() {
        if (carViewModel.getCurrentPage() < carViewModel.getTotalPageNum()) {
            mCarListRecyclerViewAdapter.addLoadingItem()
        }
        else {
            if (isRecreated == 1)
                mCarListRecyclerViewAdapter.addLoadingItem()
            // we reached last page
            isLastPage = true
        }
    }

    // loads more cars into recyclerview
    fun loadNextPage() {
        carViewModel.addMoreCars()
    }

    // gets reference to MainActivity context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
    }

    // we put "1" value into the bundle to denotes config changes
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("recreated", 1)
        super.onSaveInstanceState(outState)
    }
}