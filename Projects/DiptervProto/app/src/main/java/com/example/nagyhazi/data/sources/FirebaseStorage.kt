package com.example.nagyhazi.data.sources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import java.net.URLEncoder
import java.util.*

class FirebaseStorage {

    private lateinit var lofasz: String
    private val storageRootRef = FirebaseStorage.getInstance().reference
    private var downloadLinkMutableLiveData =  MutableLiveData<String>()

    //initializes downloadLink as an empty string
    fun initDownloadLink(): LiveData<String> {
        downloadLinkMutableLiveData.postValue("")
        return downloadLinkMutableLiveData
    }

    fun uploadImageToStorage(imageByteArray: ByteArray): LiveData<String> {
        var downloadUrlInString: String = ""
        val newImageId = URLEncoder.encode(UUID.randomUUID().toString(), "UTF-8") + ".jpg"
        val imageRef = storageRootRef.child("images/$newImageId")
        imageRef.putBytes(imageByteArray)
            .addOnFailureListener {
            }
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { link ->
                    downloadLinkMutableLiveData.postValue(link.toString())
                }
            }
        return downloadLinkMutableLiveData
    }
}