package com.example.carpricepredictor.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carpricepredictor.R
import com.example.carpricepredictor.adapter.CarAdapter
import com.example.carpricepredictor.data.DataSourceInterface
import com.example.carpricepredictor.data.FakeDataSource
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fakeDataSource = FakeDataSource()
        fakeDataSource.getListOfData()

        val layoutManager =  LinearLayoutManager(this)

        carRecycler.setLayoutManager(layoutManager)
        val adapter = CarAdapter(this, fakeDataSource.getListOfData())
        carRecycler.adapter = adapter
    }
}
