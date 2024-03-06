package com.example.phoneapp.MVVM_acrh

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.phoneapp.RoomDB.Entity.Contact

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    var repo: Repo
//    var contactList= LiveData<List<Contact>()>

    var data: LiveData<List<Contact>>

    init {
        repo = Repo(application)
        data = repo.getData()!!
    }

    fun deleteContact(contact: Contact) {
        repo.deleteData(contact)
    }

}