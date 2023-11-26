package com.app.hoichoiclone.screens.postcomments.reply.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reply_tbl")
data class Reply(
    @ColumnInfo(name = "commentsId") val comment: String = "",
    @ColumnInfo(name = "user_Id") val user_id: Int = 0,
    @ColumnInfo(name = "post_Id") val post_Id: Int = 0,
    @ColumnInfo(name = "timeStamp") val timeStamp: Long = System.currentTimeMillis()
) {
    @PrimaryKey(autoGenerate = true)
    var reply_Id: Int? = null
}
