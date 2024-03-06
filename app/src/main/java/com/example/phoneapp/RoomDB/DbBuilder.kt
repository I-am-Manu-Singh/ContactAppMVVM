package com.example.phoneapp.RoomDB

import android.content.Context

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.phoneapp.dbName

object DbBuilder {
    private var database: ApnaDatabase? = null

    fun getDB(context: Context): ApnaDatabase? {
        if (database == null) {
            database = Room.databaseBuilder(
                context,
                ApnaDatabase::class.java,
                dbName
            ).setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
        return database
    }
}