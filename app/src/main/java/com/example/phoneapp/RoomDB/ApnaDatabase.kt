package com.example.phoneapp.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.phoneapp.DB_VERSION
import com.example.phoneapp.RoomDB.DAO.ContactDAO
import com.example.phoneapp.RoomDB.Entity.Contact

@Database(entities = [Contact::class], version = DB_VERSION)
abstract class ApnaDatabase : RoomDatabase() {
    abstract fun contactDAO(): ContactDAO
}