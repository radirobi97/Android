package com.example.diptervproto.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diptervproto.R
import com.example.diptervproto.room.Car
import kotlinx.android.synthetic.main.car_item.view.*

class CarListRecyclerViewAdapter: RecyclerView.Adapter<CarListRecyclerViewAdapter.CarItemViewHolder>(){


    class CarItemDiffCallback(var oldCarList: List<Car>, var newCarList: List<Car>): DiffUtil.Callback(){
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
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
                    (oldCarList.get(oldItemPosition).carType == newCarList.get(newItemPosition).carType) &&
                    (oldCarList.get(oldItemPosition).carPrice == newCarList.get(newItemPosition).carPrice) &&
                    (oldCarList.get(oldItemPosition).carModel == newCarList.get(newItemPosition).carModel) &&
                    (oldCarList.get(oldItemPosition).carImage == newCarList.get(newItemPosition).carImage)
                    )
        }

    }


    private var carList = mutableListOf<Car>()
    var itemClickListener: CarItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return CarItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarItemViewHolder, position: Int) {
        val car = carList[position]
        holder.car = car

        holder.ivCarImage.setImageResource(car.carImage)
        holder.tvCarModel.text = car.carModel
        holder.tvCarType.text = car.carType
        holder.tvCarPrice.text = car.carPrice.toString()
    }

    override fun getItemCount(): Int = carList.size

    fun setCars(cars: List<Car>) {
        val oldList = carList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(CarItemDiffCallback(oldList, cars))
        carList = cars as MutableList<Car>
        diffResult.dispatchUpdatesTo(this)
    }

    class CarItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvCarType: TextView = itemView.tvCarType
        val tvCarModel: TextView = itemView.tvCarModel
        val tvCarPrice: TextView = itemView.tvCarPrice
        val ivCarImage: ImageView = itemView.ivCarImage

        var car: Car? = null
    }


    public interface CarItemClickListener{

    }
}