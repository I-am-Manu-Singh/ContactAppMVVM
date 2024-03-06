package com.example.phoneapp.MVVM_acrh

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import com.example.phoneapp.RoomDB.Entity.Contact

class AddEditActivityViewModel(application: Application) : AndroidViewModel(application) {
    private var repo: Repo

    init {
        repo = Repo(application)
    }

    fun storeData(contact: Contact, function: (i: Long?) -> Unit) {
        val i = repo.insertData(contact)
        function(i)
    }

    fun updateData(contact: Contact, function: (i: Int?) -> Unit) {
        function(repo.updateData(contact))
    }

}