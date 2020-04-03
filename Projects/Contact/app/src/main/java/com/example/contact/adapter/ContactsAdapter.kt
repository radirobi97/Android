package com.example.contact.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.R
import com.example.contact.model.Contact
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    private val contactList = mutableListOf<Contact>()

    var itemClickListener: ContactItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, null)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.tvContactName.text = contact.name
        holder.tvPhoneNumber.text = contact.number
        holder.contact = contact
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    fun setContacts(contacts: List<Contact>) {
        contactList.clear()
        contactList += contacts
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivContactImage: ImageView = itemView.ivContactImage
        val tvContactName: TextView = itemView.tvContactName
        val tvPhoneNumber: TextView = itemView.tvPhoneNumber

        var contact: Contact? = null

        init {
            itemView.setOnClickListener {
                contact?.let { itemClickListener?.onItemClick(it) }
            }
        }
    }

    interface ContactItemClickListener {
        fun onItemClick(contact: Contact)
    }

}