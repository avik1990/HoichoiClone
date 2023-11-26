package com.app.hoichoiclone.screens.postcomments.commentlist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comment_likes_tbl")
data class CommentsLike(
    @ColumnInfo(name = "commentsId") val comment: Int = 0,
    @ColumnInfo(name = "user_Id") val user_id: Int = 0,
    @ColumnInfo(name = "post_Id") val post_Id: Int = 0,
    @ColumnInfo(name = "like") val commentLike: Boolean = false,
    @ColumnInfo(name = "timeStamp") val timeStamp: Long = System.currentTimeMillis()
) {
    @PrimaryKey(autoGenerate = true)
    var likeId: Int? = null
}
