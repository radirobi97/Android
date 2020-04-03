package com.example.carpricepredictor.data

interface DataSourceInterface {

    fun getListOfData(): List<Car>
}