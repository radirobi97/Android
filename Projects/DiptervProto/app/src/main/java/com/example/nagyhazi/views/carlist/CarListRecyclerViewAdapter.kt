package com.example.nagyhazi.views.carlist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nagyhazi.R
import com.example.nagyhazi.data.models.Car
import kotlinx.android.synthetic.main.car_item.view.*
import kotlinx.android.synthetic.main.progress_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CarListRecyclerViewAdapter(private val activityContext: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val CAR_ITEM = 100
    private val LOADING_ITEM = 200
    private var isLoadingItemAdded = false
    private var carList = mutableListOf<Car>()

    class CarItemDiffCallback(var oldCarList: List<Car>, var newCarList: List<Car>): DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val temp = (oldCarList.get(oldItemPosition).carId == newCarList.get(newItemPosition).carId)
            Log.d("tesztelunk", temp.toString())
            return (oldCarList.get(oldItemPosition).carId == newCarList.get(newItemPosition).carId)
        }

        override fun getOldListSize(): Int {
            return oldCarList.size
        }

        override fun getNewListSize(): Int {
            return newCarList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (
                    (oldCarList.get(oldItemPosition).carId == newCarList.get(newItemPosition).carId) &&
                    (oldCarList.get(oldItemPosition).type == newCarList.get(newItemPosition).type) &&
                    (oldCarList.get(oldItemPosition).price == newCarList.get(newItemPosition).price) &&
                    (oldCarList.get(oldItemPosition).model == newCarList.get(newItemPosition).model) &&
                    (oldCarList.get(oldItemPosition).imgUrl == newCarList.get(newItemPosition).imgUrl)
                    )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == carList.size-1 && isLoadingItemAdded) LOADING_ITEM else CAR_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CAR_ITEM -> {
                val carItemView = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
                CarItemViewHolder(carItemView)
            }
            LOADING_ITEM -> {
                val loadingItemView = LayoutInflater.from(parent.context).inflate(R.layout.progress_item, parent, false)
                LoadingItemViewHolder(loadingItemView)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
                CarItemViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val car = carList[position]
        when (getItemViewType(position)) {
            CAR_ITEM -> {
                val carHolder = holder as CarItemViewHolder
                holder.tvCarModel.text = car.model
                holder.tvCarType.text = car.type
                holder.tvCarPrice.text = car.price
                GlobalScope.launch(Dispatchers.Main) {
                    Glide.with(activityContext).load(car.imgUrl).into(holder.ivCarImage)
                }
            }
            LOADING_ITEM -> {
                Log.d("PAGINATION_LOG", "PROGRESS BAR VISIBLE");
                val loadingHolder = holder as LoadingItemViewHolder
                loadingHolder.progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return if (carList == null)
            0
        else
            carList.size
    }

    fun addLoadingItem() {
        Log.d("PAGINATION_LOG", "ADD LOADING FOOTER")
        isLoadingItemAdded = true
        addCar(Car())
    }

    fun removeLoadingItem() {
        Log.d("PAGINATION_LOG", "REMOVING LOADING FOOTER");
        isLoadingItemAdded = false
        var position = carList.size-1
        var result = carList[position]
        if (result != null) {
            carList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun addCar(car: Car) {
        carList.add(car)
        notifyItemInserted(carList.size-1)
    }

    fun setCars(cars: List<Car>) {
        val newCars = mutableListOf<Car>()
        newCars.addAll(cars)
        val oldList = carList
        Log.d("tesztelunk", "A setCars parameterben kapott meret: " + cars.size.toString())
        Log.d("tesztelunk", "A korabbi lista merete: " + carList.size.toString())
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(CarItemDiffCallback(oldList, cars))
        carList = newCars
        diffResult.dispatchUpdatesTo(this)
    }

    class CarItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvCarType: TextView = itemView.tvCarType
        val tvCarModel: TextView = itemView.tvCarModel
        val tvCarPrice: TextView = itemView.tvCarPrice
        val ivCarImage: ImageView = itemView.ivCarImage
    }

    class LoadingItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.loadmore_progress

        init {
            Log.d("PAGINATION_LOG", "LOADING VIEW HOLDER CREATED")
        }
    }
}