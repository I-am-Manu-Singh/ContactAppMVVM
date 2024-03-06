package com.example.phoneapp.MVVM_acrh


import android.content.Context
import androidx.lifecycle.LiveData
import com.example.phoneapp.RoomDB.ApnaDatabase
import com.example.phoneapp.RoomDB.DbBuilder
import com.example.phoneapp.RoomDB.Entity.Contact

class Repo(
    var context: Context
) {
    var database: ApnaDatabase? = null

    init {
        database = DbBuilder.getDB(context)
    }

    fun getData(): LiveData<List<Contact>>? {
        return database?.contactDAO()?.readContact()
    }

    fun insertData(contact: Contact): Long? {
        return database?.contactDAO()?.createContact(contact)
    }

    fun deleteData(contact: Contact) {
        database?.contactDAO()?.deleteContact(contact)
    }

    fun updateData(contact: Contact): Int? {
        return database?.contactDAO()?.updateContact(contact)
    }
}