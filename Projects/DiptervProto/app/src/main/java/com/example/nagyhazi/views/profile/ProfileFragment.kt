package com.example.nagyhazi.views.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nagyhazi.R
import com.example.nagyhazi.utils.REQUEST_CODE_UPLOAD_ACTIVITY
import com.example.nagyhazi.views.upload.UploadActivity
import kotlinx.android.synthetic.main.fragment_profile.*

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