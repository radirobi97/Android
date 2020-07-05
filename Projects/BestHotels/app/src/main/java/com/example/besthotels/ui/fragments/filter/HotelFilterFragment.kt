package com.example.besthotels.ui.fragments.filter


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.besthotels.R
import com.example.besthotels.ui.shared.SharedViewModel
import kotlinx.android.synthetic.main.fragment_hotel_filter.*
import java.text.SimpleDateFormat
import java.util.*


class HotelFilterFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hotel_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel =  ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        defaultFilter()

        setOnClickListeners()

        setObservers()
    }

    private fun setOnClickListeners() {
        hotelFilterBackIb.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_hotelFilterFragment_to_hotelFragment)
        }
        filterAmenitiesBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_hotelFilterFragment_to_hotelAmenityFragment)
        }
        applyFilters()
        resetFilters()
        setDateFromDialog()
        setAdults()
        setRooms()
        setNights()
        setRange()
    }

    private fun applyFilters() {
        filterApplyBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_hotelFilterFragment_to_hotelFragment)
        }
    }

    private fun resetFilters() {
        filterResetBtn.setOnClickListener {
            sharedViewModel.checkIn.value = null
            sharedViewModel.adults.value = null
            sharedViewModel.rooms.value = null
            sharedViewModel.nights.value = null
            sharedViewModel.maxPrice.value = null
            sharedViewModel.hotelClass.value = null
            sharedViewModel.amenities.value = null

            defaultFilter()
        }
    }

    private fun setDateFromDialog() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        checkInTv.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                val incMonth = mMonth + 1
                val dayFormatted = String.format("%02d", mDay)
                val monthFormatted = String.format("%02d", incMonth)
                val dateString = "" + mYear + "-" + monthFormatted + "-" + dayFormatted
                sharedViewModel.setCheckIn(dateString)
            }, year, month, day)
            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1
            datePickerDialog.show()
        }
    }

    private fun setAdults() {
        adultsDec.setOnClickListener{
            val adultTextView = adultsTv.text.toString()
            var adultsInt = adultTextView.toInt()
            if(adultsInt <= 1){
                adultsInt = 1
            }else{
                adultsInt--
            }
            sharedViewModel.adults.value = adultsInt
        }

        adultsInc.setOnClickListener{
            val adultTextView = adultsTv.text.toString()
            var adultsInt = adultTextView.toInt()
            if(adultsInt >= 5){
                adultsInt = 5
            }else{
                adultsInt++
            }
            sharedViewModel.adults.value = adultsInt
        }
    }

    private fun setRooms() {
        roomsDec.setOnClickListener{
            val roomTextView = roomsTv.text.toString()
            var roomsInt = roomTextView.toInt()
            if(roomsInt <= 1){
                roomsInt = 1
            }else{
                roomsInt--
            }
            sharedViewModel.rooms.value = roomsInt
        }

        roomsInc.setOnClickListener{
            val roomTextView = roomsTv.text.toString()
            var roomsInt = roomTextView.toInt()
            if(roomsInt >= 5){
                roomsInt = 5
            }else{
                roomsInt++
            }
            sharedViewModel.rooms.value = roomsInt
        }
    }

    private fun setNights(){
        nightsDec.setOnClickListener{
            val nightTextView = nightsTv.text.toString()
            var nightsInt = nightTextView.toInt()
            if(nightsInt <= 1){
                nightsInt = 1
            }else{
                nightsInt--
            }
            sharedViewModel.nights.value = nightsInt
        }

        nightsInc.setOnClickListener{
            val nightTextView = nightsTv.text.toString()
            var nightsInt = nightTextView.toInt()
            if(nightsInt >= 5){
                nightsInt = 5
            }else{
                nightsInt++
            }
            sharedViewModel.nights.value = nightsInt
        }
    }

    private fun setRange() {

        filterPriceRange.progress = 0
        filterPriceRange.incrementProgressBy(1)
        filterPriceRange.max = 200
        filterPriceRange.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Display the current progress of SeekBar
                sharedViewModel.setMaxPrice(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        filterHotelClass.setOnRatingBarChangeListener { _, rating, _ ->
            sharedViewModel.setHotelClass(rating)
        }
    }

    private fun setObservers(){

        sharedViewModel.checkIn.observe(viewLifecycleOwner, Observer<Any>{
            if(it != null){
                checkInTv.text = it as String
            }
        })

        sharedViewModel.adults.observe(viewLifecycleOwner, Observer<Any> {
            if (it != null) {
                val adults = it as Int
                adultsTv.text = adults.toString()
            }
        })

        sharedViewModel.rooms.observe(viewLifecycleOwner, Observer<Any> {
            if (it != null) {
                val rooms = it as Int
                roomsTv.text = rooms.toString()
            }
        })

        sharedViewModel.nights.observe(viewLifecycleOwner, Observer<Any> {
            if (it != null) {
                val nights = it as Int
                nightsTv.text = nights.toString()
            }
        })

        sharedViewModel.maxPrice.observe(viewLifecycleOwner, Observer<Any> {
            if (it != null) {
                val maxPrice = it as Int
                filterPriceRange.progress = maxPrice
                budgetRatingProgressTv.text = "$" + maxPrice
            }
        })

        sharedViewModel.hotelClass.observe(viewLifecycleOwner, Observer<Any> {
            if (it != null) {
                val hotelClass = it as Float
                filterHotelClass.rating = hotelClass
            }
        })
    }

    private fun defaultFilter(){

        if(sharedViewModel.checkIn.value == null){
            val formatterCurrDateTv  =  SimpleDateFormat("yyyy-MM-dd")
            sharedViewModel.setCheckIn(formatterCurrDateTv.format(Date()).toString())
        }

        if(sharedViewModel.adults.value == null){
            sharedViewModel.setAdults(1)
        }

        if(sharedViewModel.rooms.value == null){
            sharedViewModel.setRooms(1)
        }

        if(sharedViewModel.nights.value == null){
            sharedViewModel.setNights(1)
        }

        if(sharedViewModel.maxPrice.value == null){
            sharedViewModel.setMaxPrice(30)
        }

        if(sharedViewModel.hotelClass.value == null){
            sharedViewModel.setHotelClass(3.0f)
        }
    }
}