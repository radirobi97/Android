package com.example.nagyhazi.views.upload

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nagyhazi.R
import com.example.nagyhazi.data.models.Car
import com.example.nagyhazi.utils.PICK_IMAGE_FROM_GALLERY
import com.example.nagyhazi.utils.validateHasImage
import com.example.nagyhazi.utils.validateNonEmpty
import kotlinx.android.synthetic.main.activity_upload.*
import java.io.ByteArrayOutputStream

/*
    This activity makes possible to upload cars into the database.
    AdapterView.OnItemSelectedListener used to observe click events on first spinner.
 */
class UploadActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var uploadViewModel: UploadViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        fillUpSpinner()
        spinnerType.onItemSelectedListener = this
        initUploadViewModel()
        attachOnClickListeners()
    }

    // fills up spinners with choices
    private fun fillUpSpinner() {
        ArrayAdapter.createFromResource(this, R.array.car_types, android.R.layout.simple_spinner_item)
            .also { adapter ->
                // sorts values alphabetically
                adapter.sort { first, second ->  first.toString().compareTo(second.toString())}
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerType.adapter=adapter
            }
    }

    /*
        * initializes uploadViewModel
        * second spinner values depends on the chosen item of the first spinner
            * changes of first spinner observed with observer
        * defines observer to listen downloadLink and carDetails changes
            * if downloadLink is present, carDetails can be uploaded to the database
            * if upload has finished successfully, a Toast appears
            * hides the progressBar
            * displays error/success messages
            * in case of success activity finishes
    */
    private fun initUploadViewModel() {
        uploadViewModel = ViewModelProvider(this).get(UploadViewModel::class.java)
        uploadViewModel.initCarAndDownloadLinkAndSpinnerLiveData()
        // download link availability check happens here
        val downloadLinkObserver = Observer<String> { downloadLink ->
            if (downloadLink.isNotEmpty()) {
                val carType = spinnerType.selectedItem.toString()
                val carModel = spinnerModel.selectedItem.toString()
                val carPrice = etCarPrice.text.toString()
                uploadViewModel.uploadCarDetailsToDatabase(carType, carModel, carPrice, downloadLink)
            }
        }
        // checks whether upload was successfully
        val carObserver = Observer<Car> { car ->
            if (car.isSuccess) {
                progressBarUpload.visibility = View.INVISIBLE
                Toast.makeText(this, "Upload was successful", Toast.LENGTH_LONG).show()
                // activity was started by startActivityForResult() cause calling activity needs to know when this activity finishes
                val resultIntent = Intent()
                setResult(Activity.RESULT_CANCELED, resultIntent)
                finish()
            }
        }
        // populates second spinner based on the first spinner
        val modelsByTypeObserver = Observer<ArrayList<String>> { models ->
            val newAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, models)
            newAdapter.sort { first, second -> first.toString().compareTo(second.toString()) }
            newAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerModel.adapter = newAdapter
        }

        uploadViewModel.uploadedCarLiveData.observe(this, carObserver)
        uploadViewModel.downloadLinkLiveData.observe(this, downloadLinkObserver)
        uploadViewModel.modelsByType.observe(this, modelsByTypeObserver)
    }

    // attaches the proper functions to the listeners
    private fun attachOnClickListeners() {
        btnChoose.setOnClickListener { chooseImageFromGallery() }
        btnUpload.setOnClickListener { uploadDetailsToFirebase() }
    }

    // opens gallery so user can choose an image
    private fun chooseImageFromGallery() {
        val chooseIntent = Intent(Intent.ACTION_PICK)
        chooseIntent.type = "image/*"
        startActivityForResult(chooseIntent, PICK_IMAGE_FROM_GALLERY)
    }

    // starts uploading the image to firestore and details to database
    private fun uploadDetailsToFirebase() {
        if(!validateInputs()) {
            return
        }
        // starts showing progress bar
        progressBarUpload.visibility = View.VISIBLE
        // disable click events on button while uploading
        btnUpload.isClickable = false
        val imgInByteArray = getImageByteArray()
        if (imgInByteArray != null) {
            // uploads image to Firestore
            uploadViewModel.uploadImageToFireStore(imgInByteArray)
        }
        else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
        }
    }

    // checks whether EditTexts/ImageView are empty or not
    private fun validateInputs(): Boolean = etCarPrice.validateNonEmpty() && ivCar.validateHasImage(this)

    // converts image to ByteArray if that is not null
    private fun getImageByteArray(): ByteArray? {
        if (ivCar.drawable == null) {
            return null
        }
        val bitmap: Bitmap = (ivCar.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

    // sets content of ImageView based on the chosen image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_FROM_GALLERY) {
                tvPlaceHolder.visibility = View.INVISIBLE
                ivCar.setImageURI(data?.data)
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    // modifies content of second spinner based on click event on the first spinner
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val selectedItem = p0?.getItemAtPosition(p2).toString()
        uploadViewModel.getModelsByType(selectedItem)
    }
}
