package com.example.nagyhazi.views.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.nagyhazi.R
import kotlinx.android.synthetic.main.dialog_search.*
import kotlinx.android.synthetic.main.dialog_search.view.*

class SearchDialogFragment: DialogFragment() {

    private lateinit var searchListener: SearchInputsListener
    private lateinit var activityContext: Context

    companion object {
        val SEARCH_FRAGMENT = "SearchDialogFragment"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
        try {
            searchListener = if(targetFragment != null) {
                targetFragment as SearchInputsListener
            } else {
                activity as SearchInputsListener
            }
        } catch (e: ClassCastException) {
            throw RuntimeException(e)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.btnCancel.setOnClickListener { dialog?.dismiss() }
        view.btnSearch.setOnClickListener { sendSearchParameters() }
        fillUpSpinner()
    }

    private fun sendSearchParameters() {
        val type = spinnerSearchType.selectedItem.toString()
        val model = etSearchModel.text.toString()
        val price = etSearchPrice.text.toString()
        searchListener.searchInputsCreated(type, model, price)
        dismiss()
    }

    private fun fillUpSpinner() {
        ArrayAdapter.createFromResource(activityContext, R.array.car_types, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerSearchType.adapter=adapter
            }
    }

    interface SearchInputsListener {
        fun searchInputsCreated(type: String, model: String, price: String)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(1000, 1000)
    }
}