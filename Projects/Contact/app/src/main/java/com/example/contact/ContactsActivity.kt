package com.example.contact

import android.Manifest
import android.Manifest.permission.READ_CONTACTS
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.annotation.RequiresPermission
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact.adapter.ContactsAdapter
import com.example.contact.model.Contact
import com.example.contact.util.getStringByColumnName
import kotlinx.android.synthetic.main.activity_contacts.*

class ContactsActivity : AppCompatActivity(), ContactsAdapter.ContactItemClickListener {

    companion object {
        private const val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        handleReadContactsPermission()
    }



    private fun loadContacts() {
        val contactsAdapter = ContactsAdapter()
        rvContacts.layoutManager = LinearLayoutManager(this)
        rvContacts.adapter = contactsAdapter
        contactsAdapter.setContacts(getAllContacts())
        contactsAdapter.itemClickListener = this
    }

    private fun ContentResolver.performQuery(
        @RequiresPermission.Read uri: Uri,
        projection: Array<String>? = null,
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        sortOrder: String? = null
    ): Cursor? {
        return query(uri, projection, selection, selectionArgs, sortOrder)
    }

    private fun getAllContacts(): List<Contact> {
        contentResolver.performQuery(
            uri = ContactsContract.Contacts.CONTENT_URI,
            sortOrder = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC"
        ).use { contactResultCursor ->
            return if (contactResultCursor == null) {
                emptyList()
            } else {
                getContacts(contactResultCursor)
            }
        }
    }

    private fun getContacts(contactCursor: Cursor): List<Contact> {
        val contactList = mutableListOf<Contact>()

        while (contactCursor.moveToNext()) {
            val hasPhoneNumber = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)).toInt()
            if (hasPhoneNumber != 0) {
                val id = contactCursor.getStringByColumnName(ContactsContract.Contacts._ID)
                val name = contactCursor.getStringByColumnName(ContactsContract.Contacts.DISPLAY_NAME)

                val contactPhoneNumber = getContactPhoneNumber(id)

                contactList += Contact(name, contactPhoneNumber)
            }
        }

        return contactList
    }

    private fun getContactPhoneNumber(id: String): String {
        contentResolver.performQuery(
            uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            selection = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            selectionArgs = arrayOf(id)
        ).use { phoneResultCursor ->
            return if (phoneResultCursor == null || !phoneResultCursor.moveToNext()) {
                ""
            } else {
                phoneResultCursor.getStringByColumnName(ContactsContract.CommonDataKinds.Phone.NUMBER)
            }
        }
    }

    private fun handleReadContactsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                showRationaleDialog(
                    explanation = R.string.contacts_permission_explanation,
                    onPositiveButton = this::requestContactsPermission
                )

            } else {
                // No explanation needed, we can request the permission.
                requestContactsPermission()
            }
        } else {
            loadContacts()
        }
    }

    private fun showRationaleDialog(@StringRes title: Int = R.string.rationale_dialog_title, @StringRes explanation: Int,
        onPositiveButton: () -> Unit,
        onNegativeButton: () -> Unit = this::finish
    ) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(explanation)
            .setCancelable(false)
            .setPositiveButton(R.string.proceed) { dialog, id ->
                dialog.cancel()
                onPositiveButton()
            }
            .setNegativeButton(R.string.exit) { dialog, id -> onNegativeButton() }
            .create()
        alertDialog.show()
    }

    private fun requestContactsPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(READ_CONTACTS),
            PERMISSIONS_REQUEST_READ_CONTACTS
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    loadContacts()
                } else {
                    // permission denied! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }
    }

    override fun onItemClick(contact: Contact) {
        val intent = Intent(this, SingleContactActivity::class.java)
        intent.putExtra(Contact.KEY_NAME, contact.name)
        intent.putExtra(Contact.KEY_NUMBER, contact.number)
        startActivity(intent)
    }
}
