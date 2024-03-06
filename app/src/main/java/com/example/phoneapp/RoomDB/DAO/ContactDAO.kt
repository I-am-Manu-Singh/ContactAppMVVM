package com.example.phoneapp.RoomDB.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.phoneapp.RoomDB.Entity.Contact

@Dao
interface ContactDAO {
    //create
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun createContact(contact: Contact): Long

    //update
    @Update
    fun updateContact(contact: Contact): Int

    //read
    @Query("SELECT * FROM CONTACT")
    fun readContact(): LiveData<List<Contact>>

    @Query("SELECT * FROM Contact WHERE id= :id1")
    fun readContact(id1: Int): Contact

    //delete
    @Delete
    fun deleteContact(contact: Contact)


}