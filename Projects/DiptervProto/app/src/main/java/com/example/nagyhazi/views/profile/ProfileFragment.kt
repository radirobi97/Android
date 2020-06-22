package com.example.diptervproto.views.profile

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.diptervproto.R
import com.example.diptervproto.utils.REQUEST_CODE_UPLOAD_ACTIVITY
import com.example.diptervproto.views.upload.UploadActivity
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.ByteArrayOutputStream

/*
    This fragment displays all user related things.
 */
class ProfileFragment(): Fragment() {

    companion object {
        // TAG which holds the class name
        const val TAG = "ProfileFragment"
        // method to instantiate a fragment
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnGoToUpload.setOnClickListener { goToUploadActivity() }
    }

    // opens UploadActivity
    private fun goToUploadActivity() {
        val uploadIntent = Intent(activity, UploadActivity::class.java)
        activity?.startActivityForResult(uploadIntent, REQUEST_CODE_UPLOAD_ACTIVITY)
    }
}