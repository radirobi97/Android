package com.example.carpricepredictor.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carpricepredictor.R
import com.example.carpricepredictor.data.Car
import kotlinx.android.synthetic.main.car_item.view.*

class CarAdapter : RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private val cars = mutableListOf<Car>()
    private val context: Context

    constructor(context: Context, cars: List<Car>) : super() {
        this.context = context
        this.cars.addAll(cars)
        Log.d("constructorofadapter", cars.size.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivCarLogo.setImageResource(cars[position].carImg)
        holder.tvCarBrand.text = cars[position].carBrand
        holder.tvCarModel.text = cars[position].carType
        holder.tvCarPrice.text = cars[position].carPrice.toString()
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCarLogo: ImageView = itemView.ivCarLogo
        val tvCarBrand: TextView = itemView.tvCarBrand
        val tvCarModel: TextView = itemView.tvCarModel
        val tvCarPrice: TextView = itemView.tvCarPrice
    }

}