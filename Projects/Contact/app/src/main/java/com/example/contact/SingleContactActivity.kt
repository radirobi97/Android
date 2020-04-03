package com.example.contact

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.contact.model.Contact
import kotlinx.android.synthetic.main.activity_single_contact.*
import permissions.dispatcher.*

@RuntimePermissions
class SingleContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_contact)

        tvContactName.text = intent.getStringExtra(Contact.KEY_NAME)
            ?: resources.getString(R.string.contact_name_placeholder)
        tvContactNumber.text = intent.getStringExtra(Contact.KEY_NUMBER)
            ?: resources.getString(R.string.contact_name_placeholder)

        buttonCall.setOnClickListener {
            callPhoneNumberWithPermissionCheck(tvContactNumber.text.toString())
        }
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    fun callPhoneNumber(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        startActivity(callIntent)
    }

    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    fun onCallDenied() {
        Toast.makeText(this, getString(R.string.permission_denied_call), Toast.LENGTH_SHORT).show()
    }

    @OnShowRationale(Manifest.permission.CALL_PHONE)
    fun showRationaleForCall(request: PermissionRequest) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(R.string.call_permission_explanation)
            .setCancelable(false)
            .setPositiveButton(R.string.proceed) { dialog, id -> request.proceed() }
            .setNegativeButton(R.string.exit) { dialog, id -> request.cancel() }
            .create()
        alertDialog.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}
