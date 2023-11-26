package com.app.hoichoiclone.screens.postcomments.commentlist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users_tbl", indices = [Index(value = arrayOf("userName"), unique = true)])
data class Users(
    @ColumnInfo(name = "userName") val userName: String = "",
    @ColumnInfo(name = "usersPhoto") val userPhoto: String,
    @ColumnInfo(name = "timeStamp") val timeStamp: Long = System.currentTimeMillis()
) {
    @PrimaryKey(autoGenerate = true)
    var userId: Int? = null

    override fun toString(): String {
        return userName
    }
}
