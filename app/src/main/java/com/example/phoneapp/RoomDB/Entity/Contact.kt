package com.example.phoneapp.RoomDB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Contact(
    @PrimaryKey var id: Int? = null,
    var userProfileImg: ByteArray? = null,
    var userName: String? = null,
    var userPhoneNo: String? = null,
    var userEmail: String? = null,
    //var date:Date
) : Serializable